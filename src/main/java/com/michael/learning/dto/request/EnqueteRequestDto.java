package com.michael.learning.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EnqueteRequestDto(
        @NotBlank(message = "Título é obrigatório") String titulo,
        @NotBlank(message = "Descrição é obrigatório") String descricao,
        @NotBlank(message = "Usuário é obrigatório") String usuarioId
) {
}
