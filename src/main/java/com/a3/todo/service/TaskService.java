// src/main/java/com/a3/todo/service/TaskService.java
package com.a3.todo.service;

import com.a3.todo.Task;
import com.a3.todo.TaskRepository;
import com.a3.todo.dto.TaskRequestDTO;
import com.a3.todo.dto.TaskResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Marca esta classe como um componente de Serviço do Spring
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponseDTO::new) // Converte cada Task em um TaskResponseDTO
                .collect(Collectors.toList());
    }

    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Task newTask = new Task();
        newTask.setTitle(requestDTO.title());
        newTask.setCompleted(false); // Novas tarefas sempre começam como não concluídas

        Task savedTask = taskRepository.save(newTask);
        return new TaskResponseDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o id: " + id));
        
        existingTask.setTitle(requestDTO.title());
        existingTask.setCompleted(requestDTO.completed());

        Task updatedTask = taskRepository.save(existingTask);
        return new TaskResponseDTO(updatedTask);
    }
    
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não encontrada com o id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
