package com.alanensina.basedomains.dto.user;

import java.util.UUID;

public record UserCreateResponseDTO(
        UUID userId,
        String name,
        String email
) {

}
