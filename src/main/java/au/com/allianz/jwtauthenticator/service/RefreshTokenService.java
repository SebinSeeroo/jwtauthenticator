package au.com.allianz.jwtauthenticator.service;

import au.com.allianz.jwtauthenticator.model.entity.RefreshToken;
import au.com.allianz.jwtauthenticator.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String userName);

    List<RefreshToken> getAllRefreshTokens();

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);

    void validateAndDeleteRefreshTokenIfExists(User user);
}
