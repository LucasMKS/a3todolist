package com.a3.todo.controller;

import com.a3.todo.dto.GrupoResponseDTO;
import com.a3.todo.entity.Grupo;
import com.a3.todo.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping
    public GrupoResponseDTO criarGrupo(@RequestBody Map<String, String> request) {
        String nome = request.get("nome");
        Grupo grupo = grupoService.criarGrupo(nome);
        return grupoService.toDTO(grupo);
    }

    @GetMapping
    public List<GrupoResponseDTO> listarGrupos() {
        return grupoService.listarGruposDoUsuario().stream()
                .map(grupoService::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public GrupoResponseDTO buscarPorId(@PathVariable Long id) {
        Grupo grupo = grupoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
        return grupoService.toDTO(grupo);
    }

    @DeleteMapping("/{id}")
    public void deletarGrupo(@PathVariable Long id) {
        grupoService.deletarGrupo(id);
    }
}