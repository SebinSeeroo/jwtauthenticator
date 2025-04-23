package au.com.allianz.jwtauthenticator.repository;

import au.com.allianz.jwtauthenticator.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUser_UserName(String userUserName);
}
