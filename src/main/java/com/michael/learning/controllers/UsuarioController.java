package com.michael.learning.controllers;

import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar (@RequestBody UsuarioRequestDto dto) {
        usuarioService.cadastrar(dto);
    }

}
