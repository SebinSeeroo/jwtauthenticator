package au.com.allianz.jwtauthenticator.service.impl;

import aj.org.objectweb.asm.commons.Remapper;
import au.com.allianz.jwtauthenticator.model.entity.RefreshToken;
import au.com.allianz.jwtauthenticator.model.entity.User;
import au.com.allianz.jwtauthenticator.repository.RefreshTokenRepository;
import au.com.allianz.jwtauthenticator.repository.UserRepository;
import au.com.allianz.jwtauthenticator.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(String userName) {

        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUserName(userName))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))//10
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public List<RefreshToken> getAllRefreshTokens() {
        return refreshTokenRepository.findAll();
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public void validateAndDeleteRefreshTokenIfExists(User user) {
        Optional<RefreshToken> newUser = refreshTokenRepository.findByUser_UserName(user.getUserName()).stream().findFirst();
        newUser.ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
    }
}
