package com.a3.todo.dto;

import lombok.Data;

@Data
public class AdicionarUsuarioGrupoRequest {
    private String email;
    private String funcao; // USER ou ADMIN
}
