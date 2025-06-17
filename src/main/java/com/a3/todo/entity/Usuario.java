package com.a3.todo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String funcao;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;
}