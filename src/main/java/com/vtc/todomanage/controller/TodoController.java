package com.vtc.todomanage.controller;

import com.vtc.todomanage.model.Todo;
import com.vtc.todomanage.payload.response.TodoResponse;
import com.vtc.todomanage.repository.TodoRepository;
import com.vtc.todomanage.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @PostMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!todoRepository.existsTodoByUser_Id(userDetails.getId())) {
            return ResponseEntity.badRequest().body(new TodoResponse("Error", "No data from this user"));
        }
        List<Todo> todoCollection = todoRepository.findTodosByUser_Id(userDetails.getId());

        return ResponseEntity.ok().body(todoCollection);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Todo> getDetailTodo(@PathVariable Long id) {
        // find by id
        Todo todo = todoRepository.findTodosById(id);
        return ResponseEntity.ok().body(todo);
    }

}
