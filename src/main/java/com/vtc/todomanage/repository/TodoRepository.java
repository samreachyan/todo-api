package com.vtc.todomanage.repository;

import com.vtc.todomanage.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
