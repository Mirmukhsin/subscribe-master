package org.subscribe.master.utility.mappers;

import org.mapstruct.Mapper;
import org.subscribe.master.dtos.authDTOs.RegisterResponseDTO;
import org.subscribe.master.entities.AuthUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RegisterResponseDTO userTORegisterResponseDTO(AuthUser user);
}
