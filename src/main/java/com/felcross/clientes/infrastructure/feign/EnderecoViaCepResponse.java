package com.felcross.clientes.infrastructure.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoViaCepResponse {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private Boolean erro;
}
