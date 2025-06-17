package com.a3.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a3.todo.entity.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByGrupoId(Long grupoId);
}