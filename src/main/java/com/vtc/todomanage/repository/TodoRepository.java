package com.vtc.todomanage.repository;

import com.vtc.todomanage.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    boolean existsTodoById(Long id);

    Optional<Todo> findByIdAndUser_id(Long id, Long userId);
}
