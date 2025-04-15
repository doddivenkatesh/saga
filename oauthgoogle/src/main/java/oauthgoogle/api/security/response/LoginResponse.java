package oauthgoogle.api.security.response;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginResponse {
	 private String jwtToken;

	    private String username;
	    private List<String> roles;

	    public LoginResponse(String username, List<String> roles, String jwtToken) {
	        this.username = username;
	        this.roles = roles;
	        this.jwtToken = jwtToken;
	    }

	   
	
}
