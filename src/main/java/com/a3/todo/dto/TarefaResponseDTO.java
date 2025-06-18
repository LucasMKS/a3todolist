package com.a3.todo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    private Long grupoId;
}
