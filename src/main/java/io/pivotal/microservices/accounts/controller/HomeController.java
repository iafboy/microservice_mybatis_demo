package io.pivotal.microservices.accounts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page controller.
 * 
 * @author Paul Chapman
 */
@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		System.out.println("go to index template file");
		return "index";
	}

}
