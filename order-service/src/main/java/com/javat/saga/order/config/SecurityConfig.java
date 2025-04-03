package com.javat.saga.order.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.javat.saga.order.jwt.AuthEntryPointJwt;
import com.javat.saga.order.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	// form based authencation
	// basic authencation
	// sessionmanagement in stateless

	@Autowired
	DataSource dataSource;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	/*
	 * @Bean SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
	 * throws Exception { http.authorizeHttpRequests((requests) ->
	 * requests.requestMatchers("/h2-console/**").permitAll().anyRequest().
	 * authenticated()); http.sessionManagement(session ->
	 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //
	 * http.formLogin(withDefaults()); http.httpBasic(Customizer.withDefaults());
	 * http.headers(headers -> headers.frameOptions(frameOptions ->
	 * frameOptions.sameOrigin())); http.csrf(csrf -> csrf.disable()); return
	 * http.build(); }
	 */

	/*
	 * @Bean SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
	 * throws Exception { http.authorizeHttpRequests(authorizeRequests ->
	 * authorizeRequests.requestMatchers("/h2-console/**").permitAll()
	 * .requestMatchers("/signin").permitAll()
	 * .requestMatchers("/api/public/**").permitAll().anyRequest().authenticated());
	 * http.sessionManagement(session ->
	 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 * 
	 * http.exceptionHandling(exception ->
	 * exception.authenticationEntryPoint(unauthorizedHandler)); //
	 * http.httpBasic(withDefaults()); http.headers(headers ->
	 * headers.frameOptions(FrameOptions -> FrameOptions.sameOrigin()));
	 * http.csrf(csrf -> csrf.disable());
	 * http.addFilterBefore(authenticationJwtTokenFilter(),
	 * UsernamePasswordAuthenticationFilter.class);
	 * 
	 * return http.build(); } previous
	 * 
	 * // in memory based security /*
	 * 
	 * @Bean public UserDetailsService userDetailsService() { UserDetails user1 =
	 * User.withUsername("user1").password(
	 * passwordEncoder().encode("password1")).roles("USER").build(); UserDetails
	 * admin =
	 * User.withUsername("admin").password(passwordEncoder().encode("adminpass")).
	 * roles("ADMIN").build();
	 * 
	 * JdbcUserDetailsManager userDetailsManager = new
	 * JdbcUserDetailsManager(dataSource); userDetailsManager.createUser(user1);
	 * userDetailsManager.createUser(admin); return userDetailsManager;
	 * 
	 * //return new InMemoryUserDetailsManager(user1, admin); }
	 */

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Enable CORS
				.csrf(csrf -> csrf.disable()) // Disable CSRF for API calls
				.authorizeHttpRequests(auth -> auth.requestMatchers("/signin", "/h2-console/**", "/api/public/**")
						.permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowCredentials(true);
	        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // ✅ Allow frontend origin
	        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of("http://localhost:3000")); // ✅ Allow frontend origin
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}

	@Bean
	public CommandLineRunner initData(UserDetailsService userDetailsService) {
		return args -> {
			JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;
			JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
			if (!manager.userExists("user1")) {
				UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("password1"))
						.roles("USER").build();
				userDetailsManager.createUser(user1);
			}
			if (!manager.userExists("admin")) {
				UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("adminpass"))
						.roles("ADMIN").build();
				userDetailsManager.createUser(admin);
			}

		};
	};

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}
}
