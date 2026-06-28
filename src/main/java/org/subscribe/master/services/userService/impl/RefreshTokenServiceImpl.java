package org.subscribe.master.services.userService.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.entities.AuthUser;
import org.subscribe.master.entities.RefreshToken;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.exceptionHandling.customExceptions.ResourceNotFoundException;
import org.subscribe.master.repositories.RefreshTokenRepository;
import org.subscribe.master.services.userService.RefreshTokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken generateRefreshToken(AuthUser user) {
        Optional<RefreshToken> existedRefreshToken = refreshTokenRepository.findByUserIdAndIsRevoked(user.getId(), false);
        if (existedRefreshToken.isPresent()) {
            existedRefreshToken.get().setRevoked(true);
            refreshTokenRepository.save(existedRefreshToken.get());
        }

        String refreshTokenUUID = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken(
                refreshTokenUUID,
                LocalDateTime.now().plusHours(7),
                false,
                user
        );

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken validateRefreshToken(String token) {
        RefreshToken existedToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        if (existedToken.getRevoked()) {
            throw new ConflictException("Refresh token has been revoked");
        }

        if (existedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            existedToken.setRevoked(true);
            refreshTokenRepository.save(existedToken);
            throw new ConflictException("Refresh token has expired");
        }
        return existedToken;
    }

    @Override
    public void revokeRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}
