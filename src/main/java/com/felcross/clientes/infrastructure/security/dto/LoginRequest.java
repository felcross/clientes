package com.felcross.clientes.infrastructure.security.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
