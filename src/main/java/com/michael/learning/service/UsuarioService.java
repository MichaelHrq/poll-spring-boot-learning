package com.michael.learning.service;

import com.michael.learning.documents.Usuarios;
import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void delete(String id) {
        log.info("Iniciando tentativa de remover usuário do ID: {}", id);
        Usuarios usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            log.warn("Falha na remoção: usuário do ID {} não encontrado", id);
            throw new NotFoundException("Usuário não encontrado");
        }
        log.info("Usuário do ID {} removido com sucesso", id);
        usuarioRepository.delete(usuario);
    }

    public List<Usuarios> findAll() {
        log.info("Iniciando tentativa de resgatar todos usuários cadastrados");
        List<Usuarios> usuarios = usuarioRepository.findAll();
        log.info("Resgate concluído com sucesso. Total de usuários encontrados: {}", usuarios.size());
        return usuarios;
    }

    public Usuarios findById(String id) {
        log.info("Iniciando tentativa de resgatar usuário do ID {}", id);
        Usuarios usuario = usuarioRepository.findById(id).orElseThrow(() -> {
            log.warn("Usuário do ID {} não encontrado", id);
            return new NotFoundException("Usuário não encontrado");
        });
        log.info("Usuário do ID {} resgatado com sucesso", id);
        return usuario;
    }

    public void updateUsername(String id, UsuarioRequestDto dto) {
        log.info("Iniciando tentativa de atualizar username {}", dto.username());
        Usuarios usuario = this.findById(id);
        usuario.setUsername(dto.username());
        log.info("Username {} atualizado com sucesso", dto.username());
        usuarioRepository.save(usuario);
    }

}
