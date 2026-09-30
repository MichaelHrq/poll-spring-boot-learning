package com.michael.learning.dto.response;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {
}
