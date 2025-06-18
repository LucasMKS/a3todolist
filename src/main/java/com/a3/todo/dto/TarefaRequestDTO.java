package com.a3.todo.dto;

import lombok.Data;

@Data
public class TarefaRequestDTO {
    private String titulo;
    private String descricao;
    private String status; // "PENDENTE", "EM_ANDAMENTO", "CONCLUIDA"
    private Long grupoId;
}
