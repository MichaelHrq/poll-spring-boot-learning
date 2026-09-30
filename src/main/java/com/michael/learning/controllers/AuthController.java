package com.michael.learning.controllers;

import com.michael.learning.dto.request.LoginRequestDto;
import com.michael.learning.dto.request.UsuarioRequestDto;
import com.michael.learning.dto.response.TokenResponseDto;
import com.michael.learning.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@RequestBody UsuarioRequestDto dto) {
        authenticationService.register(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponseDto login(@RequestBody LoginRequestDto dto) {
        return authenticationService.login(dto);
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@RequestBody UsuarioRequestDto dto) {
        authenticationService.updatePassword(dto);
    }

}
