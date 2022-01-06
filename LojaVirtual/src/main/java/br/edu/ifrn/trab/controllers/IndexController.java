package br.edu.ifrn.trab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/login-error")
	public String error(ModelMap model) {
		
		model.addAttribute("msgErro", "Login ou senha incorretos! Tente novamente.");
		
		return "login";
	}
	
}
