package org.subscribe.master.services.impl;

import org.springframework.stereotype.Service;
import org.subscribe.master.dtos.UserLoginDTO;
import org.subscribe.master.dtos.UserRegisterDTO;
import org.subscribe.master.entities.AuthUser;
import org.subscribe.master.enums.UserType;
import org.subscribe.master.exceptionHandling.customExceptions.BadCredentialsException;
import org.subscribe.master.exceptionHandling.customExceptions.ConflictException;
import org.subscribe.master.repositories.UserRepository;
import org.subscribe.master.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserRegisterDTO registerDTO) {
        boolean present = userRepository.findByEmail(registerDTO.email()).isPresent();
        if (present) {
            throw new ConflictException("User already registered");
        } else {

            AuthUser user = new AuthUser();
            user.setUsername(registerDTO.username());
            user.setEmail(registerDTO.email());
            user.setPassword(registerDTO.password());
            user.setUserType(UserType.USER);

            userRepository.save(user);
        }
    }

    @Override
    public void login(UserLoginDTO loginDTO) {
        AuthUser user = userRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new BadCredentialsException("Bad credentials. Email or password are incorrect"));
        if (!user.getPassword().equals(loginDTO.password())) {
            throw new BadCredentialsException("Bad credentials. Email or password are incorrect");
        }
    }
}
