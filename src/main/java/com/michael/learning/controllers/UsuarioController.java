package com.michael.learning.controllers;

import com.michael.learning.documents.Usuarios;
import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@RequestBody UsuarioRequestDto dto) {
        usuarioService.register(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable String id) {
        usuarioService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update (@PathVariable String id, @RequestBody UsuarioRequestDto dto) {
        usuarioService.update(id,dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Usuarios> findAll () {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuarios findById (@PathVariable String id) {
        return usuarioService.findById(id);
    }

}
