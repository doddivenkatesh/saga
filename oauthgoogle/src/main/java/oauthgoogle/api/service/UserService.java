package oauthgoogle.api.service;



import java.util.List;

import oauthgoogle.api.dto.UserDTO;
import oauthgoogle.api.entity.User;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);

	User findByUsername(String username);
}

