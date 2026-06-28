package org.subscribe.master.services.userService;

import org.subscribe.master.dtos.authDTOs.LoginResponseDTO;
import org.subscribe.master.dtos.authDTOs.UserLoginDTO;
import org.subscribe.master.dtos.authDTOs.UserRegisterDTO;

public interface UserService {

    void register(UserRegisterDTO registerDTO);

    LoginResponseDTO login(UserLoginDTO loginDTO);

    LoginResponseDTO generateNewAccessToken(String token);

}
