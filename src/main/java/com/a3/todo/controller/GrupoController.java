package com.a3.todo.controller;

import com.a3.todo.entity.Grupo;
import com.a3.todo.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping
    public Grupo criarGrupo(@RequestBody Grupo grupo) {
        return grupoService.criarGrupo(grupo);
    }

    @GetMapping
    public List<Grupo> listarGrupos() {
        return grupoService.listarGrupos();
    }

    @GetMapping("/{id}")
    public Grupo buscarPorId(@PathVariable Long id) {
        return grupoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
    }

    @DeleteMapping("/{id}")
    public void deletarGrupo(@PathVariable Long id) {
        grupoService.deletarGrupo(id);
    }
}