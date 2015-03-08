package obi1.fi.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obi1.fi.business.exception.FiBusinessException;
import obi1.fi.business.service.UsuarioService;
import obi1.fi.business.to.UsuarioTO;
import obi1.fi.web.exception.FiAjaxException;
import obi1.fi.web.interceptor.AuthenticationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("Login")
@Scope("request")
public final class LoginController extends AbstractController {

	@Autowired
	private AuthenticationUser authenticationUser;

	@Autowired
	private UsuarioService userService;
	
	private UsuarioTO usuarioTO; 
	
	private String strUsuarioTO = "usuarioTO";

	public LoginController() {
		usuarioTO = new UsuarioTO();
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request) {
		return getLogout(request);
	}
	
	@RequestMapping("logoutSess")
	public ModelAndView logoutSess(HttpServletRequest request) {
		request.setAttribute("msgError", "Sua sessão expirou. Favor logar novamente.");
		return getLogout(request);
	}
	
	@RequestMapping("login")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response, UsuarioTO usuarioTO) {
		
		try {
			userService.incrementaNrTentativas(usuarioTO.getEntity().getUsuaDsLogin());
			this.usuarioTO = userService.findFiCdUsuarioUSUA(usuarioTO.getEntity().getUsuaDsLogin(), usuarioTO.getEntity().getUsuaDsPwd());
			
			if (this.usuarioTO != null) {
				if (this.usuarioTO.getEntity().getUsuaNrTentativas() < 6) {
					userService.zeraNrTentativas(usuarioTO.getEntity().getUsuaDsLogin());
					authenticationUser.doLogin(request, response, this.usuarioTO);
				} 
				else {
					throw new FiBusinessException("Número de tentativas excedido. Entre em contato com a Administração do sistema para efetuar o desbloqueio do seu login");
				}
			}
			else {
				this.usuarioTO = new UsuarioTO();
			}
		}
		catch (Exception x) {
			throw new FiAjaxException(x);
		}

		return getEmptyJson();
	}

	private ModelAndView getLogout(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("tiles.login");
		
		usuarioTO = new UsuarioTO();
		model.addObject(strUsuarioTO, usuarioTO);
		
		authenticationUser.clearLogin(request);
		
		return model;
	}
}
