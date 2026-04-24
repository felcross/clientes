package com.felcross.clientes.business.service;

import com.felcross.clientes.infrastructure.exception.BusinessException;
import com.felcross.clientes.infrastructure.feign.EnderecoViaCepResponse;
import com.felcross.clientes.infrastructure.feign.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient client;

    public EnderecoViaCepResponse buscarEndereco(String cep) {
        // 1. Limpa o CEP (remove tudo que não for dígito)
        String cepLimpo = cep.replaceAll("\\D", "");

        // 2. Valida o formato
        if (cepLimpo.length() != 8) {
            // O erro ocorre porque aqui você provavelmente está usando IllegalArgumentException
            // ou não está lançando a BusinessException que o teste espera.
            throw new BusinessException("Formato de CEP inválido");
        }

        return client.buscarEndereco(processarCep(cep));
    }

    private String processarCep(String cep) {
        String cepFormatado = cep.replace(" ", "").replace("-", "");
        if (!cepFormatado.matches("\\d+") || cepFormatado.length() != 8)
            throw new IllegalArgumentException("CEP invalido, deve conter 8 digitos numericos");
        return cepFormatado;
    }
}
