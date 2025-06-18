package com.a3.todo.controller;

import com.a3.todo.dto.AdicionarUsuarioGrupoRequest;
import com.a3.todo.dto.UsuarioResponseDTO;
import com.a3.todo.entity.Usuario;
import com.a3.todo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/grupo/{grupoId}")
    public UsuarioResponseDTO adicionarUsuarioAoGrupo(@PathVariable Long grupoId,
                                                @RequestBody AdicionarUsuarioGrupoRequest request) {
        Usuario usuario = usuarioService.adicionarUsuario(grupoId, request.getEmail(), request.getFuncao());
        return usuarioService.toDTO(usuario);
    }

    @GetMapping("/grupo/{grupoId}")
    public List<UsuarioResponseDTO> listarUsuariosPorGrupo(@PathVariable Long grupoId) {
        return usuarioService.listarUsuariosPorGrupo(grupoId)
                .stream()
                .map(usuarioService::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{usuarioId}")
    public void removerUsuario(@PathVariable Long usuarioId) {
        usuarioService.removerUsuario(usuarioId);
    }

    @PatchMapping("/{usuarioId}/funcao")
    public UsuarioResponseDTO atualizarFuncao(@PathVariable Long usuarioId, @RequestParam String funcao) {
        Usuario usuario = usuarioService.atualizarFuncao(usuarioId, funcao);
        return usuarioService.toDTO(usuario);
    }

}
