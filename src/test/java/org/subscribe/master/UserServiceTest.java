package org.subscribe.master;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.subscribe.master.dtos.authDTOs.LoginResponseDTO;
import org.subscribe.master.dtos.authDTOs.UserLoginDTO;
import org.subscribe.master.dtos.authDTOs.UserRegisterDTO;
import org.subscribe.master.entities.AuthUser;
import org.subscribe.master.entities.RefreshToken;
import org.subscribe.master.enums.UserRole;
import org.subscribe.master.exceptionHandling.customExceptions.BadCredentialsException;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.repositories.UserRepository;
import org.subscribe.master.security.jwtConfiguration.JWTService;
import org.subscribe.master.services.userService.impl.RefreshTokenServiceImpl;
import org.subscribe.master.services.userService.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private RefreshTokenServiceImpl refreshTokenService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegister_throwConflictException() {
        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(new AuthUser()));

        assertThatThrownBy(() -> userService.register(new UserRegisterDTO("John", "john@gmail.com", "MR977900")))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("User already registered");

        verify(userRepository, never()).save(any(AuthUser.class));

        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void testRegister_registerSuccessfully() {
        UserRegisterDTO dto = new UserRegisterDTO("John", "john@gmail.com", "MR977900");

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");

        userService.register(dto);

        verify(userRepository, times(1)).findByEmail(dto.email());

        ArgumentCaptor<AuthUser> user = ArgumentCaptor.forClass(AuthUser.class);

        verify(userRepository, times(1)).save(user.capture());

        AuthUser savedUser = user.getValue();
        assertThat(savedUser.getEmail()).isEqualTo(dto.email());
        assertThat(savedUser.getUsername()).isEqualTo(dto.username());
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(savedUser.getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    void testLogin_whenUserNot_throwBadCredentialsException() {
        UserLoginDTO dto = new UserLoginDTO("john@gmail.com", "MR977900");

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(new AuthUser()));

        assertThatThrownBy(() -> userService.login(dto))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Bad credentials. Email or password are incorrect");

        verify(jwtService, never()).generateToken(any(AuthUser.class));

        verify(refreshTokenService, never()).generateRefreshToken(any(AuthUser.class));
    }

    @Test
    void testLogin_whenPSWDNotMatch_throwBadCredentialsException() {
        UserLoginDTO dto = new UserLoginDTO("john@gmail.com", "MR977900");
        AuthUser user = new AuthUser("John", "john@gmail.com", "encodedPassword", UserRole.USER, List.of());


        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.password(), "encodedPassword")).thenReturn(false);

        assertThatThrownBy(() -> userService.login(dto))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Bad credentials. Email or password are incorrect");

        verify(jwtService, never()).generateToken(any(AuthUser.class));

        verify(refreshTokenService, never()).generateRefreshToken(any(AuthUser.class));
    }

    @Test
    void testLogin_when_loginSuccessfully() {
        UserLoginDTO dto = new UserLoginDTO("john@gmail.com", "MR977900");
        AuthUser user = new AuthUser("John", "john@gmail.com", "encodedPassword", UserRole.USER, List.of());

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.password(), "encodedPassword")).thenReturn(true);

        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(refreshTokenService.generateRefreshToken(user)).thenReturn(new RefreshToken("refreshToken", LocalDateTime.now().plusHours(5), false, new AuthUser()));

        LoginResponseDTO login = userService.login(dto);

        verify(jwtService, times(1)).generateToken(user);
        verify(refreshTokenService, times(1)).generateRefreshToken(user);

        assertThat(login.accessToken()).isEqualTo("accessToken");
        assertThat(login.refreshToken()).isEqualTo("refreshToken");
        assertThat(login.email()).isEqualTo(user.getEmail());
    }

    @Test
    void testGenerateNewAccessToken_whenUseInvalidToken_throwException() {
        String invalidToken = "invalid or expired token";

        when(refreshTokenService.validateRefreshToken(invalidToken)).thenThrow(new ConflictException(""));

        assertThatThrownBy(() -> userService.generateNewAccessToken(invalidToken))
                .isInstanceOf(ConflictException.class);

        verify(refreshTokenService, never()).revokeRefreshToken(anyString());
        verify(jwtService, never()).generateToken(any(AuthUser.class));
        verify(refreshTokenService, never()).generateRefreshToken(any(AuthUser.class));
    }

    @Test
    void testGenerateNewAccessToken_when_generateSuccessfully() {
        String oldRefreshToken = "old-refresh-token";
        String newAccessToken = "new-access-token";
        String newRefreshToken = "new-refresh-token";

        AuthUser user = new AuthUser();
        user.setEmail("john@gmail.com");

        RefreshToken old = new RefreshToken();
        old.setToken(oldRefreshToken);
        old.setUser(user);

        RefreshToken newToken = new RefreshToken();
        newToken.setToken(newRefreshToken);
        newToken.setUser(user);

        when(refreshTokenService.validateRefreshToken(oldRefreshToken)).thenReturn(old);
        when(jwtService.generateToken(user)).thenReturn(newAccessToken);
        when(refreshTokenService.generateRefreshToken(user)).thenReturn(newToken);

        LoginResponseDTO response = userService.generateNewAccessToken(oldRefreshToken);

        assertThat(response).isNotNull();
        assertThat(response.email()).isEqualTo(user.getEmail());
        assertThat(response.refreshToken()).isEqualTo(newRefreshToken);
        assertThat(response.accessToken()).isEqualTo(newAccessToken);


        verify(refreshTokenService, times(1)).validateRefreshToken(anyString());
        verify(refreshTokenService, times(1)).revokeRefreshToken(anyString());
        verify(refreshTokenService, times(1)).generateRefreshToken(any(AuthUser.class));
        verify(jwtService, times(1)).generateToken(any(AuthUser.class));

    }
}
