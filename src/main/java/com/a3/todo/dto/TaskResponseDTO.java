// src/main/java/com/a3/todo/dto/TaskResponseDTO.java
package com.a3.todo.dto;

public record TaskResponseDTO(Long id, String title, boolean completed) {
    
    // Construtor extra que facilita a conversão de uma Entidade Task para este DTO.
    public TaskResponseDTO(com.a3.todo.Task task) {
        this(task.getId(), task.getTitle(), task.isCompleted());
    }
}

