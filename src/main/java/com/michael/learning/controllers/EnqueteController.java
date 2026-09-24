package com.michael.learning.controllers;

import com.michael.learning.documents.Enquetes;
import com.michael.learning.dto.request.EnqueteRequestDto;
import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.dto.response.EnqueteResultadoDto;
import com.michael.learning.dto.response.EnquetesDto;
import com.michael.learning.enums.StatusEnquete;
import com.michael.learning.service.EnqueteService;
import com.michael.learning.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/v1/enquetes")
public class EnqueteController {

    private final EnqueteService enqueteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar (@RequestBody EnqueteRequestDto dto) {
        enqueteService.cadastrar(dto);
    }

    @PutMapping("/{enqueteId}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public void alterarStatus(@PathVariable String enqueteId, @PathVariable StatusEnquete status) {
        enqueteService.alterarStatus(enqueteId, status);
    }

    @GetMapping
    public List<EnquetesDto> findAll () {
        return enqueteService.findAll();
    }

    @GetMapping("/{enqueteId}/resultado")
    @ResponseStatus(HttpStatus.OK)
    public EnqueteResultadoDto resultado(@PathVariable String enqueteId) {
        return enqueteService.resultado(enqueteId);
    }
}
