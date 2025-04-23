package au.com.allianz.jwtauthenticator.service;

import au.com.allianz.jwtauthenticator.model.JWTResponse;
import au.com.allianz.jwtauthenticator.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User register(User user);

    User findByUsername(String username);

    JWTResponse verifyUser(User user);
}
