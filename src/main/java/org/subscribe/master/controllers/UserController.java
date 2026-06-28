package org.subscribe.master.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.subscribe.master.dtos.authDTOs.LoginResponseDTO;
import org.subscribe.master.dtos.authDTOs.UserLoginDTO;
import org.subscribe.master.dtos.authDTOs.UserRegisterDTO;
import org.subscribe.master.services.userService.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userService.login(userLoginDTO), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody String refreshToken) {
        return new ResponseEntity<>(userService.generateNewAccessToken(refreshToken), HttpStatus.OK);
    }
}
