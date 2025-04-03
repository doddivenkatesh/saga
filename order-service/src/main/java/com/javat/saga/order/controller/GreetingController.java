package com.javat.saga.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javat.saga.order.dtos.LoginRequest;
import com.javat.saga.order.dtos.LoginResponse;
import com.javat.saga.order.jwt.JwtUtils;
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class GreetingController {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@GetMapping("/hello")
	public String sayHello() {
		return "hello";
	}

	// role based authencation

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String userEndPoint() {
		return "Hello, User!";
	}

	 @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String adminEndPoint() {
		return "Hello, Admin!";
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
		
		Authentication authencation;
		
		try {
			
			//authencate first if authenction is valid
			authencation = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		}catch(/*AuthConfigException*/ BadCredentialsException  exception) {
			Map<String,Object> map = new HashMap<>();
			map.put("message", "Bad credentials");
			map.put("status", false);
			return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
			
		}
		//then set the context and then 
		SecurityContextHolder.getContext().setAuthentication(authencation);
		UserDetails userDetails = (UserDetails) authencation.getPrincipal();
		// with the help of user details we are generation jwttoken jwt token only generated if user is authencated then 
		String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
		// we get list of roles
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		LoginResponse response = new LoginResponse(jwtToken,userDetails.getUsername(), roles);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/profile")
	public ResponseEntity<?> getUserProfile(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		UserDetails userDetails=(UserDetails) authentication.getPrincipal();
		Map<String,Object> profile = new HashMap<String, Object>();
		profile.put("username", userDetails.getUsername());
		profile.put("roles", userDetails.getAuthorities().stream()
				.map(item ->item.getAuthority()).collect(Collectors.toList()));
		
		profile.put("message", "this is user-specific content from backend");
		return ResponseEntity.ok(profile);
	}

}
