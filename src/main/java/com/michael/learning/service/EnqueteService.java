package com.michael.learning.service;

import com.michael.learning.documents.Enquetes;
import com.michael.learning.documents.OpcoesVotos;
import com.michael.learning.documents.Usuarios;
import com.michael.learning.dto.request.EnqueteRequestDto;
import com.michael.learning.dto.response.EnqueteResultadoDto;
import com.michael.learning.dto.response.EnquetesDto;
import com.michael.learning.dto.response.OpcaoVotoResultadoDto;
import com.michael.learning.enums.StatusEnquete;
import com.michael.learning.exception.BadRequestException;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.EnqueteRepository;
import com.michael.learning.repositories.OpcaoVotoRepository;
import com.michael.learning.repositories.UsuarioRepository;
import com.michael.learning.repositories.VotosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnqueteService {

    private final EnqueteRepository enqueteRepository;
    private final UsuarioRepository usuarioRepository;
    private final OpcaoVotoRepository opcaoVotoRepository;
    private final VotosRepository votosRepository;

    public void cadastrar(EnqueteRequestDto dto) {
        log.info("Inicianto tentativa de cadastrar enquente {} pelo usuário do ID {}", dto.titulo(), dto.usuarioId());
        Usuarios usuario = usuarioRepository.findById(dto.usuarioId()).orElse(null);
        if (usuario == null) {
            log.warn("Falha no cadastro: ID {} de usuário não encontrado", dto.usuarioId());
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
        log.info("Cadastro da enquete {} pelo usuariodo ID {} cadastrado com sucesso", dto.titulo(), dto.usuarioId());
    }

    public void alterarStatus(String enqueteId, StatusEnquete status) {
        log.info("Iniciando tentativa de atualizar status da enquete do ID {} para {}", enqueteId, status);
        Enquetes enquete = enqueteRepository.findById(enqueteId).orElse(null);
        if (enquete == null) {
            log.warn("Falha na atualização: Enquete do ID {} não encontrada", enqueteId);
            throw new NotFoundException("Enquete não encontrada");
        }
        if (enquete.getStatus() != StatusEnquete.ABERTA) {
            log.warn("Falha na atualização: status não está aberto");
            throw new BadRequestException("Status não está aberto");
        }
        enquete.setStatus(status);
        enqueteRepository.save(enquete);
        log.info("Status da enquete do ID {} atualizado com sucesso para {}", enqueteId, status);
    }

    public EnqueteResultadoDto resultado(String enqueteId) {
        log.info("Iniciando tentativa de resultado da enquete do ID {}", enqueteId);
        Enquetes enquete = enqueteRepository.findById(enqueteId).orElse(null);
        if (enquete == null) {
            log.warn("Falha no resultado: Enquete do ID {} não encontrada", enqueteId);
            throw new NotFoundException("Enquete não encontrada");
        }

        log.info("Resgatando opções de votos da enquete do ID {}", enqueteId);
        List<OpcoesVotos> opcoes = opcaoVotoRepository.getAllByEnqueteId(enqueteId);
        log.info("Opções de votos resgatados com sucesso! Quantidade de opções: {}", opcoes.size());

        log.info("Resgatando quantidade de votos da enquete do ID {}", enqueteId);
        Long totalGeral = votosRepository.countByEnqueteId(enqueteId);
        log.info("{} votos resgatados com sucesso!", totalGeral);


        List<OpcaoVotoResultadoDto> detalheOpcoes = opcoes.stream().map(opcao -> {
            Long votosOpcao = votosRepository.countByOpcaoVotoId(opcao.getId());
            Double porcentagem = totalGeral > 0
                    ? ((double) votosOpcao / totalGeral) * 100
                    : 0.0;
            log.debug("Opção '{}' (ID: {}) calculada: {} votos ({}%)",
                    opcao.getTitulo(), opcao.getId(), votosOpcao, String.format("%.2f", porcentagem));
            return new OpcaoVotoResultadoDto(
                    opcao.getTitulo(),
                    votosOpcao,
                    porcentagem
            );
        }).toList();

        log.info("Resultado da votação da enquete do ID {} finalizado com sucesso", enqueteId);
        return new EnqueteResultadoDto(
                enquete.getTitulo(),
                totalGeral,
                detalheOpcoes
        );
    }

    public List<EnquetesDto> findAll() {
        log.info("Iniciando tentativa de resgatar todas as enquetes cadastradas");
        List<Enquetes> enquetes = enqueteRepository.findAll();
        log.info("Enquetes resgatadas com sucesso! Quantidade encontrada: {}", enquetes.size());
        return enquetes.stream().map(enq -> {
            String nomeUsuario = usuarioRepository.findById(enq.getUsuarioId())
                    .map(Usuarios::getUsername)
                    .orElse("");
            List<String> titulosOpcoes = opcaoVotoRepository.findAllByEnqueteId(enq.getId())
                    .stream()
                    .map(OpcoesVotos::getTitulo)
                    .toList();
            return new EnquetesDto(
                    enq.getTitulo(),
                    enq.getDescricao(),
                    nomeUsuario,
                    titulosOpcoes
            );

        }).toList();
    }
}
