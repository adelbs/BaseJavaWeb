package obi1.fi.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("Home")
@Scope("request")
public final class HomeController extends AbstractController {

	public HomeController() { }

	@RequestMapping("load")
	public ModelAndView load(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("tiles.home");
		
		if (getCurrentUser(request) != null) {
			
		}
		
		return model;
	}
	
}
