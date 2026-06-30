package org.subscribe.master.dtos.authDTOs;

import java.time.LocalDateTime;

public record RegisterResponseDTO(
        String username,
        String email,
        LocalDateTime createdAt
) {
}
