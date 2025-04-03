package com.javat.saga.order.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

	private String jwtToken;
	private String username;
	private List<String> roles;
	
//	public LoginResponse(String username,String jwtToken,List<String> roles) {
//		this.username = username;
//		this.roles= roles;
//		this.jwtToken= jwtToken;
//	}
	
}
