package org.subscribe.master.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.subscribe.master.dtos.authDTOs.*;
import org.subscribe.master.services.userService.UserService;

@RestController
@RequestMapping("/users")
@Tag(name = "User Registration API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register user")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.OK);
    }

    @Operation(summary = "Login to system")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userService.login(userLoginDTO), HttpStatus.OK);
    }

    @Operation(summary = "Getting access token by refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody String refreshToken) {
        return new ResponseEntity<>(userService.generateNewAccessToken(refreshToken), HttpStatus.OK);
    }

    @Operation(summary = "Logging out from system")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody UserLogoutDTO logoutDTO) {
        userService.logout(logoutDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
