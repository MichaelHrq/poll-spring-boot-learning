package com.michael.learning.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VotoRequestDto(
        @NotBlank(message = "Usuário é obrigatório") String usuarioId,
        @NotBlank(message = "Enquete é obrigatória") String enqueteId,
        @NotBlank(message = "Opção de voto é obrigatória") String opcaoVotoId
) {
}
