package com.felcross.clientes.api.controller;

import com.felcross.clientes.domain.entity.Cliente;
import com.felcross.clientes.domain.repository.ClienteRepository;
import com.felcross.clientes.infrastructure.security.JwtService;
import com.felcross.clientes.infrastructure.security.dto.LoginRequest;
import com.felcross.clientes.infrastructure.security.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteRepository repository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        Cliente cliente = repository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha invalidos"));

        // por enquanto sem criptografia, depois adiciona BCrypt
        if (!cliente.getPassword().equals(req.getPassword()))
            throw new IllegalArgumentException("Email ou senha invalidos");

        return new LoginResponse(jwtService.gerarToken(cliente.getEmail()));
    }
}