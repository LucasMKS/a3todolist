package com.a3.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a3.todo.entity.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}