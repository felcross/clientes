package com.felcross.clientes.business.service;

import com.felcross.clientes.infrastructure.exception.BusinessException;
import com.felcross.clientes.infrastructure.feign.EnderecoViaCepResponse;
import com.felcross.clientes.infrastructure.feign.ViaCepClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViaCepServiceTest {

    @Mock
    private ViaCepClient client;
    @InjectMocks
    private ViaCepService viaCepService;

    @Test
    @DisplayName("Deve formatar o CEP corretamente antes de chamar o client")
    void deveFormatarCep() {
        String cepSujo = " 25000 - 000 ";
        String cepEsperado = "25000000";

        when(client.buscarEndereco(cepEsperado)).thenReturn(new EnderecoViaCepResponse());

        viaCepService.buscarEndereco(cepSujo);

        verify(client).buscarEndereco(cepEsperado);
    }

    @Test
    @DisplayName("Deve lançar BusinessException (ou IllegalArgument) para CEP com letras ou tamanho errado")
    void erroCepFormatoInvalido() {
        assertThrows(BusinessException.class, () -> viaCepService.buscarEndereco("12345-67A"));
        assertThrows(BusinessException.class, () -> viaCepService.buscarEndereco("12345"));
    }
}