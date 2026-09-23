package com.michael.learning.service;

import com.michael.learning.documents.Votos;
import com.michael.learning.dto.request.VotoRequestDto;
import com.michael.learning.exception.BadRequestException;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.EnqueteRepository;
import com.michael.learning.repositories.OpcaoVotoRepository;
import com.michael.learning.repositories.UsuarioRepository;
import com.michael.learning.repositories.VotosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotosRepository votosRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnqueteRepository enqueteRepository;
    private final OpcaoVotoRepository opcaoVotoRepository;

    public void votar(VotoRequestDto dto) throws NotFoundException {
        if (!usuarioRepository.existsById(dto.usuarioId())) {
            throw new NotFoundException("Usuário não encontrado");
        }
        if (!enqueteRepository.existsById(dto.enqueteId())) {
            throw new NotFoundException("Enquete não encontrada");
        }
        if (!opcaoVotoRepository.existsById(dto.opcaoVotoId())) {
            throw new NotFoundException("Opção de voto não encontrado");
        }

        Votos voto = votosRepository
                .findByUsuarioIdAndEnqueteId(dto.usuarioId(), dto.enqueteId())
                .orElse(null);

        if (voto != null) {
            throw new BadRequestException("Usuário já votou nessa enquete");
        }

        votosRepository.save(Votos
                .builder()
                .usuarioId(dto.usuarioId())
                .enqueteId(dto.enqueteId())
                .opcaoVotoId(dto.opcaoVotoId())
                .dtCriacao(LocalDateTime.now())
                .build()
        );
    }

}
