package com.michael.learning.service;

import com.michael.learning.documents.Enquetes;
import com.michael.learning.documents.OpcoesVotos;
import com.michael.learning.documents.Usuarios;
import com.michael.learning.dto.request.EnqueteRequestDto;
import com.michael.learning.dto.response.EnqueteResultadoDto;
import com.michael.learning.dto.response.OpcaoVotoResultadoDto;
import com.michael.learning.enums.StatusEnquete;
import com.michael.learning.exception.BadRequestException;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.EnqueteRepository;
import com.michael.learning.repositories.OpcaoVotoRepository;
import com.michael.learning.repositories.UsuarioRepository;
import com.michael.learning.repositories.VotosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EnqueteService {

    private final EnqueteRepository enqueteRepository;
    private final UsuarioRepository usuarioRepository;
    private final OpcaoVotoRepository opcaoVotoRepository;
    private final VotosRepository votosRepository;

    public void cadastrar(EnqueteRequestDto dto) {
        Usuarios usuario = usuarioRepository.findById(dto.usuarioId()).orElse(null);
        if (usuario == null) {
            throw new BadRequestException("Usuário não encontrado");
        }
        LocalDateTime now = LocalDateTime.now();
        enqueteRepository.save(Enquetes
                .builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .usuarioId(dto.usuarioId())
                .status(StatusEnquete.ABERTA)
                .dtCriacao(now)
                .dtEncerramento(now.plusDays(7))
                .build()
        );
    }

    public void alterarStatus (String enqueteId, StatusEnquete status) {
        Enquetes enquete = enqueteRepository.findById(enqueteId).orElse(null);
        if (enquete == null) {
            throw new NotFoundException("Enquete não encontrada");
        }
        if (enquete.getStatus() != StatusEnquete.ABERTA) {
            throw new BadRequestException("Status não pode ser alterado");
        }
        enquete.setStatus(status);
        enqueteRepository.save(enquete);
    }

    public EnqueteResultadoDto resultado(String enqueteId) {
        Enquetes enquete = enqueteRepository.findById(enqueteId).orElse(null);

        if (enquete == null) {
            throw new NotFoundException("Enquete não encontrada");
        }

        List<OpcoesVotos> opcoes = opcaoVotoRepository.getAllByEnqueteId(enqueteId);
        Long totalGeral = votosRepository.countByEnqueteId(enqueteId);

        List<OpcaoVotoResultadoDto> detalheOpcoes = opcoes.stream().map(opcao -> {
            Long votosOpcao = votosRepository.countByOpcaoVotoId(opcao.getId());

            Double porcentagem = totalGeral > 0
                    ?  ((double) votosOpcao/totalGeral) * 100
                    : 0.0;
            return new OpcaoVotoResultadoDto(
                    opcao.getId(),
                    opcao.getTitulo(),
                    votosOpcao,
                    porcentagem
            );
        }).toList();

        return new EnqueteResultadoDto(
                enquete.getTitulo(),
                totalGeral,
                detalheOpcoes
        );
    }

}
