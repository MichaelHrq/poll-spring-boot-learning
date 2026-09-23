package com.michael.learning.dto.response;

import java.util.List;

public record EnqueteResultadoDto(
        String titulo,
        Long totalVotos,
        List<OpcaoVotoResultadoDto> opcoes
) {
}
