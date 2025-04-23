package au.com.allianz.jwtauthenticator.service.impl;

import au.com.allianz.jwtauthenticator.model.JWTResponse;
import au.com.allianz.jwtauthenticator.model.entity.RefreshToken;
import au.com.allianz.jwtauthenticator.model.entity.User;
import au.com.allianz.jwtauthenticator.repository.UserRepository;
import au.com.allianz.jwtauthenticator.service.JWTService;
import au.com.allianz.jwtauthenticator.service.PasswordEncryptDecryptService;
import au.com.allianz.jwtauthenticator.service.RefreshTokenService;
import au.com.allianz.jwtauthenticator.service.UserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncryptDecryptService passwordEncryptDecryptService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUserName("test");
        user.setEmail("default.user@example.com");
        user.setPassword("test");
        user.setRole("USER");
        createUser(user);

        LOG.info("User created on startup: {}",  user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        return createUser(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public JWTResponse verifyUser(User user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            refreshTokenService.validateAndDeleteRefreshTokenIfExists(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserName());
            return JWTResponse.builder().accessToken(jwtService.generateToken(user.getUserName()))
                     .token(refreshToken.getToken()).build();
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


    public User createUser(User user) {
        user.setPassword(passwordEncryptDecryptService.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
