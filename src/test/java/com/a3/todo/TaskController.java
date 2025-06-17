// src/main/java/com/a3/todo/TaskController.java
package com.a3.todo;

import com.a3.todo.dto.TaskRequestDTO;
import com.a3.todo.dto.TaskResponseDTO;
import com.a3.todo.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(requestDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO requestDTO) {
        try {
            TaskResponseDTO updatedTask = taskService.updateTask(id, requestDTO);
            return ResponseEntity.ok(updatedTask);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

