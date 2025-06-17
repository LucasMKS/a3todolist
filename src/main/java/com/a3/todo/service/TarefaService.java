package com.a3.todo.service;

import com.a3.todo.entity.Grupo;
import com.a3.todo.entity.Tarefa;
import com.a3.todo.model.StatusTarefa;
import com.a3.todo.repository.GrupoRepository;
import com.a3.todo.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public Tarefa criarTarefa(Long grupoId, Tarefa tarefa) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
        tarefa.setGrupo(grupo);
        tarefa.setStatus(StatusTarefa.PENDENTE);
        tarefa.setDataCriacao(LocalDateTime.now());
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefasPorGrupo(Long grupoId) {
        return tarefaRepository.findByGrupoId(grupoId);
    }

    public Tarefa atualizarStatus(Long tarefaId, StatusTarefa novoStatus) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefa.setStatus(novoStatus);

        if (novoStatus == StatusTarefa.CONCLUIDA) {
            tarefa.setDataConclusao(LocalDateTime.now());
        } else {
            tarefa.setDataConclusao(null);
        }

        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Long tarefaId) {
        tarefaRepository.deleteById(tarefaId);
    }
}