package com.michael.learning.service;

import com.michael.learning.documents.Usuarios;
import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.UsuarioRepository;
import com.michael.learning.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void register(UsuarioRequestDto dto) throws BadRequestException {
        Usuarios usuario = usuarioRepository.findByUsername(dto.username()).orElse(null);
        if (usuario != null) {
            throw new BadRequestException("Username já cadastrado!");
        }
        usuarioRepository.save(Usuarios.builder().username(dto.username()).build());
    }

    public void delete(String id) {
        Usuarios usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }
        usuarioRepository.delete(usuario);
    }

    public List<Usuarios> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuarios findById(String id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public void update(String id, UsuarioRequestDto dto) {
        Usuarios usuario = this.findById(id);
        usuario.setUsername(dto.username());
        usuarioRepository.save(usuario);
    }

}
