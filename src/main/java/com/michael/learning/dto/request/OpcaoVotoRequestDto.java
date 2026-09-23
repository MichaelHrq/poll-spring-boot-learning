package com.michael.learning.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OpcaoVotoRequestDto(
        @NotBlank(message = "Título é obrigatório") String titulo,
        @NotBlank(message = "Enquete é obrigatória") String enqueteId
) {
}
