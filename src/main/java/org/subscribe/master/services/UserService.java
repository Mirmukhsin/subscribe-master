package org.subscribe.master.services;

import org.subscribe.master.dtos.UserLoginDTO;
import org.subscribe.master.dtos.UserRegisterDTO;

public interface UserService {

    void register(UserRegisterDTO registerDTO);

    void login(UserLoginDTO loginDTO);

}
