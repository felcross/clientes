package com.felcross.clientes.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    @NotBlank private String nome;
    @NotBlank @Email private String email;
    @NotBlank private String cpf;
    @NotBlank private String password;
    private String telefone;
    @NotBlank @Pattern(regexp = "\\d{5}-?\\d{3}") private String cep;
}
