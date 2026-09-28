package com.michael.learning.controllers;

import com.michael.learning.documents.Votos;
import com.michael.learning.dto.request.VotoRequestDto;
import com.michael.learning.dto.response.VotosEnqueteDto;
import com.michael.learning.service.VotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/v1/votos")
public class VotosController {

    private final VotoService votoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void vote (@RequestBody VotoRequestDto dto) {
        votoService.vote(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VotosEnqueteDto findAllByEnquete (String enqueteID) {
        return votoService.findAllByEnquete(enqueteID);
    }

}
