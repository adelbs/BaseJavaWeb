package obi1.fi.web.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import obi1.fi.business.entity.FiCdUsuarioUSUA;
import obi1.fi.business.exception.FiBusinessException;
import obi1.fi.business.to.AbstractTO;
import obi1.fi.common.exception.FiException;
import obi1.fi.common.util.MessageSourceUtil;
import obi1.fi.web.exception.FiAjaxException;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class AbstractController {
	
	@Autowired
	private ApplicationContext context;

	@Autowired
	private MessageSource message;
	
	private Logger logger = Logger.getLogger(AbstractController.class);
	
	private final String msgError = "msgError";
	private final String msgSuccess = "msgSuccess";
	
	private String wrap = "\n";
	
	protected final FiCdUsuarioUSUA getCurrentUser(HttpServletRequest request) {
		return (FiCdUsuarioUSUA) request.getSession().getAttribute("FiCdUsuarioUSUA");
	}
	
	@ExceptionHandler(FiException.class)
	public final String handleException(Exception ex, HttpServletRequest request) {
		if (request.getAttribute(msgError) == null || "".equals(request.getAttribute(msgError))) {
			String errorMsg = "";
			if (ex.getMessage() != null && !"".equals(ex.getMessage())) {
				errorMsg = ex.getMessage();
			}
			
			if (ex.getCause() != null && ex.getCause().getMessage() != null && !"".equals(ex.getCause().getMessage())) {
				errorMsg += wrap + ex.getCause().getMessage();
			}
			
			if ("".equals(errorMsg)) {
				errorMsg = getStringStacktrace(ex);
			}
			
			setMessageErrorSession(request, errorMsg);
		}
		
		loggerException(ex);
		return getUrlToForward(request);
	}
	
	/**
	 * Pega a URL anterior do servlet, para redirecionar em caso de erro.
	 * @param request request
	 * @return url para redirecionamento
	 */
	protected final String getUrlToForward(HttpServletRequest request) {
		String refer = "redirect:" + request.getHeader("Referer");
		
		String scheme = request.getScheme().concat("://");
		String hostname = request.getRequestURL().toString().split(scheme)[1].split("/")[0];
		String url = scheme + hostname + request.getContextPath() + request.getServletPath() + "?";
		
		if (refer != null) {
			final int index = refer.indexOf('?');
			if (index > -1) {
				if (refer.split("&").length < 1) {
					refer = refer.substring(0, index) + '?';
				}
			} 
			else {
				refer += "?";
			}
			url = refer;	
		}
		
		final Enumeration<String> enumeration = request.getParameterNames();
		String parameters = "";

		while (enumeration.hasMoreElements()) {
			final String name = (String) enumeration.nextElement();
			
			if (url.contains(name + "=") || name.contains("Pwd")) {
				continue;
			}
			
			parameters += name + "=" + request.getParameter(name) + "&";
		}
		
		if (url.contains("=")) {
			parameters = "&" + parameters;
		}
		
		url += parameters;
		url = url.charAt(url.length() - 1) == '&' ? url.substring(0, url.length() - 2) : url;
		
		return url;
	}
	
	@ExceptionHandler(FiAjaxException.class)
	@ResponseBody
	public final String handleAjaxException(Exception ex, HttpServletRequest request) {
		FiAjaxException ajaxException;
		Exception exCause = (Exception) ex.getCause();
		
		loggerException(exCause);
		JSONObject jsonObject = new JSONObject();
		
		try {
			ajaxException = (FiAjaxException) ex;
			if (ajaxException.getCustomMessage() != null && !"".equals(ajaxException.getCustomMessage())) {
				jsonObject.put("messageError", ajaxException.getCustomMessage());
			}
			else {
				jsonObject.put("messageError", formatErrorMessageDialog(exCause.getMessage()));
				
				if (!(exCause instanceof FiBusinessException)) {
					if (exCause.getCause() != null) {
						jsonObject.put("stackTrace", getStringStacktrace(exCause.getCause()));
					}
					else {
						jsonObject.put("stackTrace", getStringStacktrace(exCause));
					}
				}
			}
		}
		catch (Exception x) {
			logger.error(x);
			throw new FiException(x);
		}
		
		return jsonObject.toString();
	}
	
	private String formatErrorMessageDialog(String message) {
		String result = message;
		if (result.indexOf(":") > 0) {
			result = result.split(":")[0];
		}
		
		return result;
	}
	
	private String getStringStacktrace(Throwable ex) {
		final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    ex.printStackTrace(printWriter);
		return result.toString().replace(wrap, "@@").replace("\r", "");
	}
	
	private void loggerException(Exception ex) {
		if (!(ex instanceof FiBusinessException)) {
			logger.error("Error", ex);
		}
	}

	protected final void setMessageErrorSession(HttpServletRequest request, String msg) {
		request.getSession().setAttribute(msgError, msg);
	}

	protected final void setMessageError(HttpServletRequest request, String msg) {
		request.setAttribute(msgError, msg);
	}
	
	protected final void setMessageSuccess(HttpServletRequest request, String msg) {
		request.setAttribute(msgSuccess, msg);
	}
	
	@SuppressWarnings("rawtypes")
	protected final String getJsonResultTable(AbstractTO to) {
		Map<String, Object> jsonMap;
		jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put("resultTableTO", to.getResultTable());
		return new JSONObject(jsonMap).toString();
	}
	
	protected final String getJsonSuccess(String message) {
		Map<String, Object> jsonMap;
		jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put("messageSuccess", MessageSourceUtil.getMessage(message));
		return new JSONObject(jsonMap).toString();
	}

	protected final String getJsonError(String message) {
		Map<String, Object> jsonMap;
		jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put("messageError", MessageSourceUtil.getMessage(message));
		return new JSONObject(jsonMap).toString();
	}

	protected final String getEmptyJson() {
		Map<String, Object> jsonMap;
		jsonMap = new LinkedHashMap<String, Object>();
		jsonMap.put("", "");
		return new JSONObject(jsonMap).toString();
	}

	public boolean isAjaxCall(HttpServletRequest request) {
		return methodHasAnnotation(request, ResponseBody.class);
	}
	
	@SuppressWarnings("rawtypes")
	private boolean methodHasAnnotation(HttpServletRequest request, Class a) {
		boolean hasAnnotation = false;
		String requestedUrl = getRequestedMethod(request);
		String url = "";

		for (Method method : this.getClass().getMethods()) {
			hasAnnotation = false;
			for (Annotation ann : method.getAnnotations()) {
				if (!hasAnnotation) {
					if ("ResponseBody".equals(a.getSimpleName()) && ann instanceof ResponseBody) {
						hasAnnotation = true;
					}
				}
				
				if (ann instanceof RequestMapping) {
					url = ((RequestMapping) ann).value()[0];
				}
				
				if (hasAnnotation && requestedUrl.equals(url)) {
					break;
				}
			}
			
			if (hasAnnotation && requestedUrl.equals(url)) {
				break;
			}
		}
		
		return hasAnnotation;
	}
	
	private String getRequestedMethod(HttpServletRequest request) {
		String[] urlArr;
		urlArr = request.getRequestURL().toString().split("/");
		return urlArr[urlArr.length - 1];
	}
	
	public final ApplicationContext getContext() {
		return context;
	}
}
