package oauthgoogle.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;


@Component
public class FrontendUrlProvider {
	
//	 @Value("${frontend.url}")
//	    private String frontendUrl;
//
//	    public String getFrontendUrl() {
//	    	  System.out.println(frontendUrl);
//	        return frontendUrl;
//	      
//	    }
	  @Value("${frontend.url}")
	    private String frontendUrl;

	    @PostConstruct
	    public void logUrl() {
	        System.out.println("Frontend URL is: " + frontendUrl);
	    }
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}
