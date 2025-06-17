// src/main/java/com/a3/todo/repository/TaskRepository.java
package com.a3.todo.repository; // <- Pacote corrigido

import com.a3.todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
