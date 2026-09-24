package com.michael.learning.service;

import com.michael.learning.documents.Enquetes;
import com.michael.learning.documents.OpcoesVotos;
import com.michael.learning.dto.request.OpcaoVotoRequestDto;
import com.michael.learning.enums.StatusEnquete;
import com.michael.learning.exception.BadRequestException;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.EnqueteRepository;
import com.michael.learning.repositories.OpcaoVotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpcaoVotoService {

    private final OpcaoVotoRepository opcaoVotoRepository;
    private final EnqueteRepository enqueteRepository;

    public void register(OpcaoVotoRequestDto dto) {
        Enquetes enquete = enqueteRepository.findById(dto.enqueteId())
                .orElseThrow(() -> new NotFoundException("Enquete não encontrada"));
        if (enquete.getStatus() != StatusEnquete.ABERTA) {
            throw new BadRequestException("Enquete não está aberta");
        }
        opcaoVotoRepository.save(OpcoesVotos
                .builder()
                .titulo(dto.titulo())
                .enqueteId(dto.enqueteId())
                .build()
        );
    }

    public List<OpcoesVotos> findAllByEnquete(String enqueteId) {
        if (!enqueteRepository.existsById(enqueteId)) {
            throw new NotFoundException("Enquete não encontrada");
        }
        return opcaoVotoRepository.findAllByEnqueteId(enqueteId);
    }

    public void delete(String id) {
        OpcoesVotos opcao = opcaoVotoRepository.findById(id).orElse(null);
        if (opcao == null) {
            throw new NotFoundException("Opção de voto não encontrada");
        }
        opcaoVotoRepository.delete(opcao);
    }

}
