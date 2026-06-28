package org.subscribe.master.dtos.authDTOs;

public record LoginResponseDTO(
        String accessToken,
        String refreshToken,
        String email
) {
}
