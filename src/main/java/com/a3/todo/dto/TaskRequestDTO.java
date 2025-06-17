// src/main/java/com/a3/todo/dto/TaskRequestDTO.java
package com.a3.todo.dto; // <- Pacote corrigido

// DTO para receber dados de criação e atualização de tarefas
public record TaskRequestDTO(String title, Boolean completed) {
}
