package com.michael.learning.service;

import com.michael.learning.documents.Usuarios;
import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.repositories.UsuarioRepository;
import com.michael.learning.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void cadastrar(UsuarioRequestDto dto) throws BadRequestException {
        Usuarios usuario = usuarioRepository.findByUsername(dto.username()).orElse(null);
        if (usuario != null) {
            throw new BadRequestException("Username já cadastrado");
        }
        usuarioRepository.save(Usuarios.builder().username(dto.username()).build());
    }

}
