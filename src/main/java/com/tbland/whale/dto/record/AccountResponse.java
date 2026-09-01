package com.tbland.whale.dto.record;

public record AccountResponse(
        Integer id,
        String username,
        String email,
        String photo,
        boolean activated
) {
}
