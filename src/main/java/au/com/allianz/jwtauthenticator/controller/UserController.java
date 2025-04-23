package au.com.allianz.jwtauthenticator.controller;

import au.com.allianz.jwtauthenticator.model.JWTResponse;
import au.com.allianz.jwtauthenticator.model.entity.User;
import au.com.allianz.jwtauthenticator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${auth.login.endpoint:/api/users}")
@ConditionalOnProperty(name = "auth.api.enabled", havingValue = "true", matchIfMissing = true)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);

    }

    @PostMapping("/login")
    public JWTResponse login(@RequestBody User user) {
        return userService.verifyUser(user);
    }

}
