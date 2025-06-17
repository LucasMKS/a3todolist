// src/main/java/com/a3/todo/TaskRepository.java
package com.a3.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca esta interface como um componente de Repositório do Spring
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Nenhum código aqui é necessário!
    // O Spring Data JPA implementa os métodos CRUD (Create, Read, Update, Delete)
    // automaticamente com base no tipo <Task, Long> (Entidade Task, ID do tipo Long).
}

