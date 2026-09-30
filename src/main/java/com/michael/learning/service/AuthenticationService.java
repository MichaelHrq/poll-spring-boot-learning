package com.michael.learning.service;

import com.michael.learning.config.jwt.TokenProvider;
import com.michael.learning.documents.Roles;
import com.michael.learning.documents.Usuarios;
import com.michael.learning.dto.request.LoginRequestDto;
import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.dto.response.TokenResponseDto;
import com.michael.learning.enums.RolesEnum;
import com.michael.learning.exception.BadRequestException;
import com.michael.learning.exception.NotFoundException;
import com.michael.learning.repositories.RolesRepository;
import com.michael.learning.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public void register(UsuarioRequestDto dto) {
        log.info("Iniciando tentativa de registro para o username: {}", dto.username());
        Usuarios usuario = usuarioRepository.findByUsername(dto.username()).orElse(null);
        if (usuario != null) {
            log.warn("Falha no registro: username {} já cadastrado", dto.username());
            throw new BadRequestException("Username já cadastrado!");
        }

        Roles role = rolesRepository.findByRole(RolesEnum.ROLE_USER)
                .orElseGet(()-> Roles
                        .builder()
                        .role(RolesEnum.ROLE_USER.toString())
                        .build()
                );

        usuarioRepository.save(Usuarios.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .roles(Set.of(role))
                .build());
        log.info("Username {} registrado e salvo com sucesso", dto.username());
    }

    public void updatePassword(UsuarioRequestDto dto) {
        log.info("Iniciando tentativa de atualizar senha do username: {}", dto.username());
        Usuarios usuario = usuarioRepository.findByUsername(dto.username())
                .orElseThrow(() -> {
                    log.warn("Falha na atualização: Username {} não encontrado", dto.username());
                    return new NotFoundException("Usuário não encontrado");
                });
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuarioRepository.save(usuario);
        log.info("Senha do username {} atualizada com sucesso", dto.username());
    }

    public TokenResponseDto login (LoginRequestDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
            return tokenProvider.gerarToken(authentication);
        } catch (AuthenticationException e) {
            throw new BadRequestException("Credenciais inválidas");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
