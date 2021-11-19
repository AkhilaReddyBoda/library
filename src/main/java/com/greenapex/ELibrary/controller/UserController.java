package com.greenapex.ELibrary.controller;

import java.security.Principal;
import java.util.List;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

//import com.akhilaBank.AkhilaBank.models.Customer;
import com.greenapex.ELibrary.model.User;
import com.greenapex.ELibrary.repository.UserRepository;
import com.greenapex.ELibrary.service.UseService;

@Controller
public class UserController {
	@Autowired
	public UseService userService;

	@Autowired
	private UserRepository userrepo;

	@GetMapping
	public String homePage() {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "registration_form";

	}

	@PostMapping("/process_register")
	public String registration(User user, Model model) {
		List<User> users = userrepo.findAll();
		if (users.isEmpty()) {
			user.setRole("Admin");
		} else {
			user.setRole("User");
		}

		userService.addUser(user);
		return "register_success";

	}

	@GetMapping("/login")
	    public String viewLoginPage() {
	        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
	        if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
	        	 
	        return "login";
	    }
	        return "redirect:/";
	}
	@GetMapping("/logout")
	public String viewLogoutPage() {
		return "logout";
	}

	@GetMapping("/home")
	public String homepage(Principal principal, Model model) {
		User user = userService.getCustomerDetails(principal.getName());
		model.addAttribute("User", user);
		model.addAttribute("Role", user.getRole());
		if (user.getRole().equals("Admin")) {
			return "home_admin";
		} else if (user.getRole().equals("User")) {
			return "home_user";
		} else {
			return null;
		}

	}

}
