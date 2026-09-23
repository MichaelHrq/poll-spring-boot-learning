package com.michael.learning.dto.response;

public record OpcaoVotoResultadoDto(
        String opcaoId,
        String titulo,
        Long totalVotos,
        Double porcentagem
) {
}
