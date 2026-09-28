package com.michael.learning.dto.response;

import java.util.List;

public record VotosEnqueteDto (
        String enqueteId,
        String enquete,
        List<UsuarioOpcaoVotoDto> votos
) {
}
