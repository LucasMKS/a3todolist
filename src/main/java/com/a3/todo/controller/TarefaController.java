package com.a3.todo.controller;

import com.a3.todo.dto.TarefaRequestDTO;
import com.a3.todo.dto.TarefaResponseDTO;
import com.a3.todo.service.TarefaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listarTarefas() {
        List<TarefaResponseDTO> tarefas = tarefaService.listarTarefas();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<TarefaResponseDTO>> listarPorGrupo(@PathVariable Long grupoId) {
        List<TarefaResponseDTO> tarefas = tarefaService.listarPorGrupo(grupoId);
        return ResponseEntity.ok(tarefas);
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody TarefaRequestDTO request) {
        TarefaResponseDTO criada = tarefaService.criarTarefa(request);
        return new ResponseEntity<>(criada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(@PathVariable Long id, @RequestBody TarefaRequestDTO request) {
        try {
            TarefaResponseDTO atualizada = tarefaService.atualizarTarefa(id, request);
            return ResponseEntity.ok(atualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        try {
            tarefaService.deletarTarefa(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
