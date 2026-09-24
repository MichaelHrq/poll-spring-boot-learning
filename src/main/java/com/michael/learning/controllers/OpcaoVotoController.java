package com.michael.learning.controllers;

import com.michael.learning.documents.OpcoesVotos;
import com.michael.learning.dto.request.OpcaoVotoRequestDto;
import com.michael.learning.service.OpcaoVotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/v1/opcoes-votos")
public class OpcaoVotoController {

    private final OpcaoVotoService opcaoVotoService;

    @GetMapping("/enquete/{enqueteId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OpcoesVotos> findAllByEnquete (@PathVariable String enqueteId) {
        return opcaoVotoService.findAllByEnquete(enqueteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody OpcaoVotoRequestDto dto) {
        opcaoVotoService.register(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete (@PathVariable String id) {
        opcaoVotoService.delete(id);
    }

}
