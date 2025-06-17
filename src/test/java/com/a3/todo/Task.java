// src/main/java/com/a3/todo/Task.java
package com.a3.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Marca esta classe como uma tabela no banco de dados
public class Task {

    @Id // Marca o campo 'id' como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o valor do ID automaticamente
    private Long id;

    private String title;
    private boolean completed;

    // Construtor vazio (necessário para o JPA)
    public Task() {
    }

    // Getters e Setters (necessários para acessar e modificar os dados)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

