// src/main/java/com/a3/todo/controller/TaskController.java
package com.a3.todo.controller; // <- Pacote corrigido

import com.a3.todo.model.StatusTarefa;
import com.a3.todo.entity.Tarefa;
import com.a3.todo.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping("/grupo/{grupoId}")
    public Tarefa criarTarefa(@PathVariable Long grupoId, @RequestBody Tarefa tarefa) {
        return tarefaService.criarTarefa(grupoId, tarefa);
    }

    @GetMapping("/grupo/{grupoId}")
    public List<Tarefa> listarTarefasPorGrupo(@PathVariable Long grupoId) {
        return tarefaService.listarTarefasPorGrupo(grupoId);
    }

    @PatchMapping("/{tarefaId}/status")
    public Tarefa atualizarStatus(@PathVariable Long tarefaId, @RequestParam StatusTarefa status) {
        return tarefaService.atualizarStatus(tarefaId, status);
    }

    @DeleteMapping("/{tarefaId}")
    public void deletarTarefa(@PathVariable Long tarefaId) {
        tarefaService.deletarTarefa(tarefaId);
    }
}