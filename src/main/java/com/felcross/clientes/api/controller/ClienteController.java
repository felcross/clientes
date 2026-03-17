package com.felcross.clientes.api.controller;

import com.felcross.clientes.api.dto.*;
import com.felcross.clientes.business.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {
    private final ClienteService service;

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar cliente")
    public ClienteResponse criar(@Valid @RequestBody ClienteRequest req) { return service.criar(req); }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ClienteResponse buscar(@PathVariable Long id) { return service.buscarPorId(id); }

    @GetMapping
    @Operation(summary = "Listar clientes")
    public List<ClienteResponse> listar() { return service.listar(); }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    public ClienteResponse atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar cliente")
    public void deletar(@PathVariable Long id) { service.deletar(id); }
}
