package com.a3.todo.service;

import com.a3.todo.dto.TarefaRequestDTO;
import com.a3.todo.dto.TarefaResponseDTO;
import com.a3.todo.entity.Grupo;
import com.a3.todo.model.StatusTarefa;
import com.a3.todo.entity.Tarefa;
import com.a3.todo.repository.GrupoRepository;
import com.a3.todo.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public List<TarefaResponseDTO> listarTarefas() {
        return tarefaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TarefaResponseDTO> listarPorGrupo(Long grupoId) {
        return tarefaRepository.findByGrupoId(grupoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TarefaResponseDTO criarTarefa(TarefaRequestDTO request) {
        Grupo grupo = grupoRepository.findById(request.getGrupoId())
                .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());
        tarefa.setStatus(StatusTarefa.valueOf(request.getStatus()));
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setGrupo(grupo);

        if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
            tarefa.setDataConclusao(LocalDateTime.now());
        }

        Tarefa salva = tarefaRepository.save(tarefa);
        return toDTO(salva);
    }

    public TarefaResponseDTO atualizarTarefa(Long id, TarefaRequestDTO request) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        Grupo grupo = grupoRepository.findById(request.getGrupoId())
                .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));

        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());
        tarefa.setGrupo(grupo);

        StatusTarefa novoStatus = StatusTarefa.valueOf(request.getStatus());
        tarefa.setStatus(novoStatus);

        if (novoStatus == StatusTarefa.CONCLUIDA) {
            tarefa.setDataConclusao(LocalDateTime.now());
        } else {
            tarefa.setDataConclusao(null);
        }

        Tarefa atualizada = tarefaRepository.save(tarefa);
        return toDTO(atualizada);
    }

    public void deletarTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        tarefaRepository.delete(tarefa);
    }

    private TarefaResponseDTO toDTO(Tarefa tarefa) {
        TarefaResponseDTO dto = new TarefaResponseDTO();
        dto.setId(tarefa.getId());
        dto.setTitulo(tarefa.getTitulo());
        dto.setDescricao(tarefa.getDescricao());
        dto.setStatus(tarefa.getStatus().name());
        dto.setDataCriacao(tarefa.getDataCriacao());
        dto.setDataConclusao(tarefa.getDataConclusao());
        dto.setGrupoId(tarefa.getGrupo().getId());
        return dto;
    }
}
