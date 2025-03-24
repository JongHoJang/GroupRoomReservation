package com.manchung.grouproom.repository;

import com.manchung.grouproom.entity.RefreshToken;
import com.manchung.grouproom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String refreshToken);

    Optional<RefreshToken> findByUser(User user);
}
