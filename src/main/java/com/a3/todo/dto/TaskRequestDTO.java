// src/main/java/com/a3/todo/dto/TaskRequestDTO.java
package com.a3.todo.dto;

// Usamos um 'record' em Java, que é uma forma concisa de criar classes imutáveis
// Ele já cria construtores, getters, equals(), hashCode() e toString() automaticamente.
public record TaskRequestDTO(String title, Boolean completed) {
}
