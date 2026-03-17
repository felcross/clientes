package com.felcross.clientes.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Endereco {
    @Column(length = 9)
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    @Column(length = 2)
    private String uf;
}
