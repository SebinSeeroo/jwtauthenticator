package au.com.allianz.jwtauthenticator.controller;

import au.com.allianz.jwtauthenticator.model.JWTResponse;
import au.com.allianz.jwtauthenticator.model.RefreshTokenRequest;
import au.com.allianz.jwtauthenticator.model.entity.RefreshToken;
import au.com.allianz.jwtauthenticator.model.entity.User;
import au.com.allianz.jwtauthenticator.service.JWTService;
import au.com.allianz.jwtauthenticator.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/refrehtoken")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JWTService jwtService;

    @GetMapping
    public List<RefreshToken> getAllUsers() {
        return refreshTokenService.getAllRefreshTokens();
    }

    @PostMapping("/loginWithRefreshToken")
    public JWTResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken());
        if (refreshToken.isPresent()) {
            return refreshToken
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(userInfo -> {
                        String accessToken = jwtService.generateToken(userInfo.getUserName());
                        return JWTResponse.builder()
                                .accessToken(accessToken)
                                .token(refreshTokenRequest.getToken())
                                .build();
                    }).orElseThrow(() -> new RuntimeException(
                            "Refresh token is not in database!"));

        } else {
            throw new RuntimeException(refreshTokenRequest.getToken() + " Refresh token could not be found");
        }
    }

}
