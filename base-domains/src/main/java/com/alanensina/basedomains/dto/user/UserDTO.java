package com.alanensina.basedomains.dto.user;

import com.alanensina.basedomains.dto.order.OrderDTO;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID userId,
        String name,
        String email,
        List<OrderDTO> orders
) {
}
