package com.michael.learning.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDto(
        @NotBlank(message = "Username é obrigatório") String username
) {
}
