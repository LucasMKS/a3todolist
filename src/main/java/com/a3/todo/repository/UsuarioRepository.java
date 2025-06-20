package com.a3.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a3.todo.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}