package com.a3.todo.controller;

import com.a3.todo.entity.Usuario;
import com.a3.todo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/grupo/{grupoId}")
    public Usuario adicionarUsuario(@PathVariable Long grupoId, @RequestBody Usuario usuario) {
        return usuarioService.adicionarUsuario(grupoId, usuario);
    }

    @GetMapping("/grupo/{grupoId}")
    public List<Usuario> listarUsuariosPorGrupo(@PathVariable Long grupoId) {
        return usuarioService.listarUsuariosPorGrupo(grupoId);
    }

    @DeleteMapping("/{usuarioId}")
    public void removerUsuario(@PathVariable Long usuarioId) {
        usuarioService.removerUsuario(usuarioId);
    }

    @PatchMapping("/{usuarioId}/funcao")
    public Usuario atualizarFuncao(@PathVariable Long usuarioId, @RequestParam String funcao) {
        return usuarioService.atualizarFuncao(usuarioId, funcao);
    }
}