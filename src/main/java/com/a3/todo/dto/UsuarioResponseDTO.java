package com.a3.todo.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String funcao;
    private Long grupoId;
    private String grupoNome;
}
