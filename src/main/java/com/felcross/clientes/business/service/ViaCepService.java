package com.felcross.clientes.business.service;

import com.felcross.clientes.infrastructure.feign.EnderecoViaCepResponse;
import com.felcross.clientes.infrastructure.feign.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient client;

    public EnderecoViaCepResponse buscarEndereco(String cep) {
        return client.buscarEndereco(processarCep(cep));
    }

    private String processarCep(String cep) {
        String cepFormatado = cep.replace(" ", "").replace("-", "");
        if (!cepFormatado.matches("\\d+") || cepFormatado.length() != 8)
            throw new IllegalArgumentException("CEP invalido, deve conter 8 digitos numericos");
        return cepFormatado;
    }
}
