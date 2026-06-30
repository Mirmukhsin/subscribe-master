package org.subscribe.master.services.userService.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.subscribe.master.dtos.authDTOs.*;
import org.subscribe.master.entities.AuthUser;
import org.subscribe.master.entities.RefreshToken;
import org.subscribe.master.enums.UserRole;
import org.subscribe.master.exceptionHandling.customExceptions.BadCredentialsException;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.repositories.UserRepository;
import org.subscribe.master.security.jwtConfiguration.JWTService;
import org.subscribe.master.services.userService.RefreshTokenService;
import org.subscribe.master.services.userService.UserService;
import org.subscribe.master.utility.mappers.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService, RefreshTokenService refreshTokenService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Override
    public RegisterResponseDTO register(UserRegisterDTO registerDTO) {
        boolean present = userRepository.findByEmail(registerDTO.email()).isPresent();
        if (present) {
            throw new ConflictException("User already registered");
        }

        AuthUser user = new AuthUser();
        user.setUsername(registerDTO.username());
        user.setEmail(registerDTO.email());
        user.setPassword(passwordEncoder.encode(registerDTO.password()));
        user.setRole(UserRole.USER);

        userRepository.save(user);

        return userMapper.userTORegisterResponseDTO(user);
    }

    @Override
    @Transactional
    public LoginResponseDTO login(UserLoginDTO loginDTO) {
        AuthUser user = userRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new BadCredentialsException("Bad credentials. Email or password are incorrect"));
        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials. Email or password are incorrect");
        }

        refreshTokenService.revokeAllTokens(user.getId());

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);
        return new LoginResponseDTO(
                accessToken,
                refreshToken.getToken(),
                user.getEmail()
        );
    }

    @Override
    public LoginResponseDTO generateNewAccessToken(String token) {
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(token);
        AuthUser user = refreshToken.getUser();

        refreshTokenService.revokeRefreshToken(token);

        String newAccessToken = jwtService.generateToken(user);

        RefreshToken newRefreshToken = refreshTokenService.generateRefreshToken(user);

        return new LoginResponseDTO(
                newAccessToken,
                newRefreshToken.getToken(),
                user.getEmail()
        );
    }

    @Override
    public void logout(UserLogoutDTO logoutDTO) {
        if (logoutDTO.refreshToken().isEmpty()) {
            throw new ConflictException("Bad request");
        }
        refreshTokenService.revokeRefreshToken(logoutDTO.refreshToken());
    }
}
