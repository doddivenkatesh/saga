package com.javat.saga.order.config.corseconfig;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
/*@Component
public class CorsFilter implements Filter {
	
	/* @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
	            throws IOException, ServletException {
	        HttpServletResponse res = (HttpServletResponse) response;
	        HttpServletRequest req = (HttpServletRequest) request;

	        res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
	        res.setHeader("Access-Control-Allow-Credentials", "true");

	        // Handle preflight OPTIONS request
	        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
	            res.setStatus(HttpServletResponse.SC_OK);
	            return;
	        }

	        chain.doFilter(request, response);
	    }
	
}

	/*@Bean
	@Primary
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .cors(cors -> cors.configurationSource(request -> {
	            CorsConfiguration config = new CorsConfiguration();
	            config.setAllowedOrigins(List.of("http://localhost:3000"));
	            config.setAllowCredentials(true);
	            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	            config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
	            return config;
	        }))
	        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

	    return http.build();
	}
}


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Set allowed origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // Enable credentials like cookies
            }
        };
    }
}

*/