package obi1.fi.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obi1.fi.business.datasource.DataSourceProperties;
import obi1.fi.business.datasource.FiDataSource;
import obi1.fi.business.service.DbConnectService;
import obi1.fi.web.controller.DBController;
import obi1.fi.web.controller.LoginController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public final class GenericHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

	@Autowired
	private AuthenticationUser authenticationUser;
	
	@Autowired
	private DbConnectService service;

	private String strMsgError = "msgError";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (request.getSession().getAttribute(strMsgError) != null) {
			request.setAttribute(strMsgError, request.getSession().getAttribute(strMsgError));
			request.getSession().setAttribute(strMsgError, null);
		}

		//Se não estiver conectado, redireciona para a tela de configuração de conexão
		if (!FiDataSource.isConnected()) {
			DataSourceProperties dsProperties = new DataSourceProperties();
			dsProperties.configDataSource();
			service.checkSchema(dsProperties);
			service.checkDefaultRecords(dsProperties);
		}
		
		//Se estiver conectado, verifica se usuário está logado
		if (!(handler instanceof LoginController) && !(handler instanceof DBController)) {
			authenticationUser.initAuthentication(request, response, handler);
		}

		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	//	request.getSession().setAttribute("isErrorRedirect", false);
	}

}
