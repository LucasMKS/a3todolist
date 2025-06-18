package com.a3.todo.dto;

import lombok.Data;

@Data
public class AuthRegisterRequest {
    private String nome;
    private String email;
    private String senha;
    private String funcao;
}
