package obi1.fi.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obi1.fi.business.to.UsuarioTO;
import obi1.fi.common.exception.FiException;
import obi1.fi.common.util.FiUtils;

import org.springframework.stereotype.Service;

@Service
public final class AuthenticationUser {
	
	public void initAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			String execaoLoginURL = "";
			String[] urlArr;
			String url;
			urlArr = request.getRequestURL().toString().split("/");
			url = urlArr[urlArr.length - 2].concat("/").concat(urlArr[urlArr.length - 1]);
			
			if (request.getSession().getAttribute("FiCdUsuarioUSUA") == null) {
				if (execaoLoginURL.indexOf(url) < 0) {
					if (request.getRequestURL().toString().indexOf("Home/load") < 0) {
						FiUtils.redirectPage(request, response, "/Login/logoutSess");
					}
					else {
						FiUtils.redirectPage(request, response, "/Login/logout");
					}
				}
			}
		}
		catch (Exception e) {
			throw new FiException(e);
		}
	}
	
	public void clearLogin(HttpServletRequest request) {
		request.getSession().setAttribute("FiCdUsuarioUSUA", null);
		request.getSession().setAttribute("HashPermissao", null);
	}

	public void doLogin(HttpServletRequest request, HttpServletResponse response, UsuarioTO usuarioTO) {
		request.getSession().setAttribute("FiCdUsuarioUSUA", usuarioTO.getEntity());
	}
	
}
