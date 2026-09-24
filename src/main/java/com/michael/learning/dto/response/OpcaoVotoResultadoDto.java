package com.michael.learning.dto.response;

public record OpcaoVotoResultadoDto(
        String titulo,
        Long totalVotos,
        Double porcentagem
) {
}
