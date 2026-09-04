package com.tbland.whale.exception;

public record ErrorResponse(
        int status,
        String message
) {
}
