package com.michael.learning.service;

import com.michael.learning.documents.OpcoesVotos;
import com.michael.learning.dto.request.OpcaoVotoRequestDto;
import com.michael.learning.exception.BadRequestException;
import com.michael.learning.repositories.OpcaoVotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpcaoVotoService {

    private final OpcaoVotoRepository opcaoVotoRepository;

    public void cadastrar(OpcaoVotoRequestDto dto) throws BadRequestException {
        opcaoVotoRepository.save(OpcoesVotos
                .builder()
                .titulo(dto.titulo())
                .enqueteId(dto.enqueteId())
                .build()
        );
    }

}
