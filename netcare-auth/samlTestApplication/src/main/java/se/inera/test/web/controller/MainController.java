package se.inera.test.web.controller;

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
//		MedicalPersonalUserImpl mUser =
//				 (MedicalPersonalUserImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		model.addAttribute("username", mUser.getUsername());
//		model.addAttribute("name", mUser.getName());
//		model.addAttribute("isDoctor", mUser.isDoctor());
//		model.addAttribute("careUnits", mUser.getCareUnits().size());
//		model.addAttribute("details", mUser.toString());
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

}
