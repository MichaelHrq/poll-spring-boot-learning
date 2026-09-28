package com.michael.learning.service;

import com.michael.learning.documents.Enquetes;
import com.michael.learning.documents.OpcoesVotos;
import com.michael.learning.documents.Usuarios;
import com.michael.learning.documents.Votos;
import com.michael.learning.dto.request.VotoRequestDto;
import com.michael.learning.dto.response.UsuarioOpcaoVotoDto;
import com.michael.learning.dto.response.VotosEnqueteDto;
import com.michael.learning.exception.BadRequestException;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.EnqueteRepository;
import com.michael.learning.repositories.OpcaoVotoRepository;
import com.michael.learning.repositories.UsuarioRepository;
import com.michael.learning.repositories.VotosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class VotoService {

    private final VotosRepository votosRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnqueteRepository enqueteRepository;
    private final OpcaoVotoRepository opcaoVotoRepository;

    public void vote(VotoRequestDto dto) {
        log.info("Iniciando tentativa de cadastrar um voto");

        log.info("Resgatando usuário (ID: {})", dto.usuarioId());
        if (!usuarioRepository.existsById(dto.usuarioId())) {
            log.warn("Falha na votação: usuário não encontrado (ID: {})", dto.usuarioId());
            throw new NotFoundException("Usuário não encontrado");
        }

        log.info("Resgatando enquete (ID: {})", dto.enqueteId());
        if (!enqueteRepository.existsById(dto.enqueteId())) {
            log.warn("Falha na votação: enquete não encontrada (ID: {})", dto.enqueteId());
            throw new NotFoundException("Enquete não encontrada");
        }

        log.info("Resgatando opção de voto (ID: {})", dto.opcaoVotoId());
        if (!opcaoVotoRepository.existsById(dto.opcaoVotoId())) {
            log.warn("Falha na votação: opção de voto não encontrado (ID: {})", dto.opcaoVotoId());
            throw new NotFoundException("Opção de voto não encontrado");
        }

        Votos voto = votosRepository
                .findByUsuarioIdAndEnqueteId(dto.usuarioId(), dto.enqueteId())
                .orElse(null);
        if (voto != null) {
            log.warn("Falha na votação: usuário já votou nessa enquete (ID Usuário: {}) (ID Enquete: {})",
                    dto.usuarioId(), dto.enqueteId());
            throw new BadRequestException("Usuário já votou nessa enquete");
        }

        votosRepository.save(Votos.builder()
                .usuarioId(dto.usuarioId())
                .enqueteId(dto.enqueteId())
                .opcaoVotoId(dto.opcaoVotoId())
                .dtCriacao(LocalDateTime.now())
                .build()
        );
        log.info("Voto registrado com sucesso");
    }

    public VotosEnqueteDto findAllByEnquete(String enqueteID) {
        log.info("Iniciando tentativa de resgate de todos os votos da enquete (ID: {})", enqueteID);
        Enquetes enquete = enqueteRepository.findById(enqueteID).orElse(null);
        if (enquete == null) {
            log.warn("Falha no resgate de votos: enquete não encontrada (ID: {})", enqueteID);
            throw new NotFoundException("Enquete não encontrada");
        }

        List<Votos> votos = votosRepository.findAllByEnqueteId(enqueteID);
        log.debug("Total de votos resgatados para a enquete {}: {}", enqueteID, votos.size());

        List<OpcoesVotos> opcoes = opcaoVotoRepository.findAllByEnqueteId(enqueteID);
        Map<String, String> mapOpcoes = opcoes.stream()
                .collect(Collectors.toMap(OpcoesVotos::getId, OpcoesVotos::getTitulo));
        log.debug("Dicionário de opções mapeado na memória. Total de opções: {}", opcoes.size());

        Set<String> idsDosEleitores = votos.stream()
                .map(Votos::getUsuarioId)
                .collect(Collectors.toSet());
        List<Usuarios> usuarios = usuarioRepository.findAllById(idsDosEleitores);
        Map<String,String> mapUsuarios = usuarios.stream()
                .collect(Collectors.toMap(Usuarios::getId, Usuarios::getUsername));
        log.debug("Dicionário de usuários mapeado na memória. Total de usuários: {}", usuarios.size());

        VotosEnqueteDto votosEnquete = new VotosEnqueteDto(
                enqueteID,
                enquete.getTitulo(),
                votos.stream().map(voto -> {
                    String username = mapUsuarios.getOrDefault(voto.getUsuarioId(), "Usuário desconhecido");
                    String tituloOpcao = mapOpcoes.getOrDefault(voto.getOpcaoVotoId(), "Opção de voto desconhecido");
                    log.debug("Mapeando voto: Usuário [{}] votou na opção [{}]", username, tituloOpcao);
                    return new UsuarioOpcaoVotoDto(username, tituloOpcao);
                }).toList()
        );
        log.info("Resgate de votos da enquete (ID: {}) concluído com sucesso. DTO montado.", enqueteID);
        return votosEnquete;
    }

}
