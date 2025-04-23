package au.com.allianz.jwtauthenticator.service;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncryptDecryptService {
    String encode(String password);

    PasswordEncoder getPasswordEncoder();
}
