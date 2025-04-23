package au.com.allianz.jwtauthenticator.service.impl;

import au.com.allianz.jwtauthenticator.service.PasswordEncryptDecryptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptDecryptServiceImpl implements PasswordEncryptDecryptService {

    @Value("${security.password.encoder.strength:12}")
    private int encoderStrength;


    @Override
    public String encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(encoderStrength);
        return encoder.encode(password);
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(encoderStrength);
    }
}
