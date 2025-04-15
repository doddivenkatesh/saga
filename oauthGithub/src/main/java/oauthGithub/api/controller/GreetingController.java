package oauthGithub.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@GetMapping("/hello")
	public String sayHello() {
		return "hello";
	}
	
	 @GetMapping("/secure")
	    public String secureEndpoint(Authentication authentication) {
	        return "Hello " + authentication.getName() + ", this is a secured endpoint!";
	    }
}
