package com.vtc.todomanage.controller;

import com.vtc.todomanage.model.Todo;
import com.vtc.todomanage.repository.TodoRepository;
import com.vtc.todomanage.repository.UserRepository;
import com.vtc.todomanage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getList() {
        Optional<Set<Todo>> todos = userRepository.findById(userService.userAuthenticated().getId()).map(user -> user.getTodos());

        return ResponseEntity.ok().body(todos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetailTodo(@PathVariable Long id) {
        // find by id
        Optional<Todo> todo = todoRepository.findById(id);
        // check is owned by user_id
        if (todo.get().getUser().getId().equals(userService.userAuthenticated().getId())) {
            return ResponseEntity.ok().body(todo);
        }

        return ResponseEntity.ok().body("Not found");
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        // Error
        userRepository.findById(userService.userAuthenticated().getId()).map(user -> {
            todo.setUser(user);
            return ResponseEntity.ok().body(todoRepository.save(todo));
        });

        return ResponseEntity.ok().body(todo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> editTodo(@PathVariable Long id, @Valid @RequestBody Todo todo) {
        // find by id
        if (!todoRepository.existsTodoById(id)) {
            return ResponseEntity.badRequest().body("Not Found");
        }
        // update
        Optional<Todo> todoUpdated = todoRepository.findById(id).map(td -> {
            td.setTitle(todo.getTitle());
            td.setContent(todo.getContent());
            return todoRepository.save(td);
        });

        return ResponseEntity.ok().body(todoUpdated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        // find by id
        if (!todoRepository.existsTodoById(id)) {
            return ResponseEntity.badRequest().body("Not found");
        }
        return todoRepository.findByIdAndUser_id(id, userService.userAuthenticated().getId()).map(todo -> {
           todoRepository.delete(todo);
           return ResponseEntity.ok().body("Deleted");
        }).orElse(ResponseEntity.badRequest().body("Error while deleting! You dont have permission to delete"));
    }
}
