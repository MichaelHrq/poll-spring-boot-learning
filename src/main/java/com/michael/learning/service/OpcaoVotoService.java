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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpcaoVotoService {

    private final OpcaoVotoRepository opcaoVotoRepository;
    private final EnqueteRepository enqueteRepository;

    public void register(OpcaoVotoRequestDto dto) {
        log.info("Iniciando tentativa de registrar opção de voto [{}] da enquete [{}]", dto.titulo(), dto.enqueteId());
        Enquetes enquete = enqueteRepository.findById(dto.enqueteId())
                .orElseThrow(() -> {
                    log.warn("Falha no registro: enquete [{}] não encontrada", dto.enqueteId());
                    return  new NotFoundException("Enquete não encontrada");
                });
        if (enquete.getStatus() != StatusEnquete.ABERTA) {
            log.warn("Falha no registro: enquete {} não está aberta", dto.enqueteId());
            throw new BadRequestException("Enquete não está aberta");
        }
        opcaoVotoRepository.save(OpcoesVotos
                .builder()
                .titulo(dto.titulo())
                .enqueteId(dto.enqueteId())
                .build()
        );
        log.info("Opção de voto registrado com sucesso");
    }

    public List<OpcoesVotos> findAllByEnquete(String enqueteId) {
        log.info("Iniciando tentativa de resgatar todas opções de votos da enquete (ID: {})", enqueteId);
        if (!enqueteRepository.existsById(enqueteId)) {
            log.warn("Falha no registro: enquete {} não está aberta", enqueteId);
            throw new NotFoundException("Enquete não encontrada");
        }
        List<OpcoesVotos> opcoes = opcaoVotoRepository.findAllByEnqueteId(enqueteId);
        log.info("Resgate das opções de votos da enquete {} com sucesso", enqueteId);
        return opcoes;
    }

    public void delete(String id) {
        log.info("Iniciando tentativa de remover opção de voto [{}]", id);
        OpcoesVotos opcao = opcaoVotoRepository.findById(id).orElse(null);
        if (opcao == null) {
            log.warn("Falha na remoção: opção de voto não encontrada [{}]", id);
            throw new NotFoundException("Opção de voto não encontrada");
        }
        opcaoVotoRepository.delete(opcao);
        log.info("Opção de voto removida com sucesso");
    }

}
