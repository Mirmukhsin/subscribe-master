package org.subscribe.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.subscribe.master.entities.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Transactional
    @Modifying
    @Query("update RefreshToken rt set rt.isRevoked = true, rt.expiresAt = current_timestamp " +
            "where rt.user.id = :userId " +
            "and rt.isRevoked = false ")
    void revokeAllUserTokens(Long userId);

    Optional<RefreshToken> findByUserIdAndIsRevoked(Long userId, Boolean isRevoked);

    Optional<RefreshToken> findByToken(String token);
}
