package oauthGithub.api.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.AuthorizeRequestsDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain deSecurityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf -> csrf.disable()) 
		.authorizeHttpRequests(authorizeRequests -> 
		authorizeRequests.anyRequest().authenticated())
		.oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/hello",true));
		//.formLogin(form -> form.defaultSuccessUrl("/hello",true));
		
		return http.build();
	}
}
