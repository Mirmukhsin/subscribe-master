package org.subscribe.master.services.userService;

import org.subscribe.master.dtos.authDTOs.*;

public interface UserService {

    RegisterResponseDTO register(UserRegisterDTO registerDTO);

    LoginResponseDTO login(UserLoginDTO loginDTO);

    LoginResponseDTO generateNewAccessToken(String token);

    void logout(UserLogoutDTO logoutDTO);

}
