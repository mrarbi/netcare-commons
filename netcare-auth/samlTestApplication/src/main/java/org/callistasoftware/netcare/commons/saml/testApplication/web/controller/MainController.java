package org.callistasoftware.netcare.commons.saml.testApplication.web.controller;

//import org.callistasoftware.netcare.commons.auth.api.implementation.MedicalPersonalUserImpl;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class MainController {
	
	@RequestMapping("/hi")
	public String index(ModelMap model) {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

}
