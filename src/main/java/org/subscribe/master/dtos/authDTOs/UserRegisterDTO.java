package org.subscribe.master.dtos.authDTOs;

public record UserRegisterDTO(
        String username,
        String email,
        String password
) {
}
