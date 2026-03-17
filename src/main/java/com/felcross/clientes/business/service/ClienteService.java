package com.felcross.clientes.business.service;

import com.felcross.clientes.api.dto.*;
import com.felcross.clientes.business.mapper.ClienteMapper;
import com.felcross.clientes.domain.entity.Cliente;
import com.felcross.clientes.domain.repository.ClienteRepository;
import com.felcross.clientes.infrastructure.feign.EnderecoViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final ViaCepService viaCepService;
    private final ClienteMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public ClienteResponse criar(ClienteRequest req) {
        if (repository.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email ja cadastrado");
        if (repository.existsByCpf(req.getCpf()))
            throw new IllegalArgumentException("CPF ja cadastrado");

        EnderecoViaCepResponse viacep = viaCepService.buscarEndereco(req.getCep());
        if (viacep.isErro()) throw new IllegalArgumentException("CEP invalido");

        Cliente cliente = mapper.toEntity(req);
        cliente.setEndereco(mapper.toEndereco(viacep));
        cliente.setPassword(passwordEncoder.encode(req.getPassword()));
        return mapper.toResponse(repository.save(cliente));
    }

    public ClienteResponse buscarPorId(Long id) {
        return mapper.toResponse(findOrThrow(id));
    }

    public List<ClienteResponse> listar() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public ClienteResponse atualizar(Long id, ClienteRequest req) {
        Cliente c = findOrThrow(id);
        c.setNome(req.getNome());
        c.setTelefone(req.getTelefone());
        return mapper.toResponse(repository.save(c));
    }

    public void deletar(Long id) { repository.delete(findOrThrow(id)); }

    private Cliente findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado"));
    }
}