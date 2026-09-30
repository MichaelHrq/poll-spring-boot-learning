package com.michael.learning.dto.response;

import java.util.List;

public record EnquetesDto(
        String id,
        String titulo,
        String descricao,
        String usuario,
        List<String> opcoes
) {
}
