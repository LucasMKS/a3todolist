// src/main/java/com/a3/todo/dto/TaskResponseDTO.java
package com.a3.todo.dto; // <- Pacote corrigido

import com.a3.todo.entity.Task;

// DTO para enviar dados de tarefas para o cliente
public record TaskResponseDTO(Long id, String title, boolean completed) {
    
    // Construtor que converte uma Entidade em um DTO de resposta
    public TaskResponseDTO(Task task) {
        this(task.getId(), task.getTitle(), task.isCompleted());
    }
}
