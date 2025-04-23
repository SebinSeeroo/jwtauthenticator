package au.com.allianz.jwtauthenticator.repository;

import au.com.allianz.jwtauthenticator.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
}
