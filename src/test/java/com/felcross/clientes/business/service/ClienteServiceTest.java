package com.felcross.clientes.business.service;

import com.felcross.clientes.api.dto.ClienteRequest;
import com.felcross.clientes.api.dto.ClienteResponse;
import com.felcross.clientes.business.mapper.ClienteMapper;
import com.felcross.clientes.domain.entity.Cliente;
import com.felcross.clientes.domain.repository.ClienteRepository;
import com.felcross.clientes.infrastructure.exception.ConflictException;
import com.felcross.clientes.infrastructure.exception.ResourceNotFoundException;
import com.felcross.clientes.infrastructure.feign.EnderecoViaCepResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;
    @Mock private ViaCepService viaCepService;
    @Mock private ClienteMapper mapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve criar um cliente com sucesso quando dados e CEP forem válidos")
    void criarComSucesso() {
        // GIVEN
        ClienteRequest req = ClienteRequest.builder().email("test@test.com").cpf("123").cep("25000000").password("123").build();
        EnderecoViaCepResponse cepMock = new EnderecoViaCepResponse(); // isErro = false por padrão

        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);
        when(viaCepService.buscarEndereco(anyString())).thenReturn(cepMock);
        when(passwordEncoder.encode(anyString())).thenReturn("senha_hash");
        when(mapper.toEntity(any())).thenReturn(new Cliente());
        when(repository.save(any())).thenReturn(new Cliente());
        when(mapper.toResponse(any())).thenReturn(ClienteResponse.builder().id(1L).build());

        // WHEN
        ClienteResponse res = clienteService.criar(req);

        // THEN
        assertNotNull(res.getId());
        verify(repository).save(any());
        verify(passwordEncoder).encode("123");
    }

    @Test
    @DisplayName("Deve lançar ConflictException ao tentar cadastrar e-mail duplicado")
    void erroEmailDuplicado() {
        // GIVEN
        ClienteRequest req = ClienteRequest.builder().email("duplicado@test.com").build();
        when(repository.existsByEmail(req.getEmail())).thenReturn(true);

        // WHEN & THEN
        assertThrows(ConflictException.class, () -> clienteService.criar(req));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o ViaCep retorna erro")
    void erroCepInvalido() {
        // GIVEN
        ClienteRequest req = ClienteRequest.builder().cep("00000-000").build();
        EnderecoViaCepResponse cepErro = new EnderecoViaCepResponse();
        cepErro.setErro(true); // Simula o retorno "erro: true" do ViaCep

        when(viaCepService.buscarEndereco(anyString())).thenReturn(cepErro);

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> clienteService.criar(req));
    }
}