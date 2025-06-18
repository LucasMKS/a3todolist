package com.a3.todo.dto;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String senha;
}
