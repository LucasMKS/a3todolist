package com.a3.todo.dto;

import lombok.Data;

import java.util.List;

@Data
public class GrupoResponseDTO {
    private Long id;
    private String nome;
    private List<UsuarioSimplesDTO> usuarios;
    private List<TarefaSimplesDTO> tarefas;
}
