package com.vtc.todomanage.repository;

import com.vtc.todomanage.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo findTodosById(Long id);

    List<Todo> findTodosByUser_Id(Long userid);

    boolean existsTodoByUser_Id(Long id);

    boolean existsTodoById(Long id);
}
