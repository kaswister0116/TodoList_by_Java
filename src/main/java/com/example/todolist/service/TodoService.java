
package com.example.todolist.service;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public List<Todo> getAll() {
        return repo.findAll().stream()
            .sorted((a, b) -> {
                if (!a.isCompleted() && b.isCompleted()) return -1;
                if (a.isCompleted() && !b.isCompleted()) return 1;
                return a.getCreatedAt().compareTo(b.getCreatedAt());
            }).toList();
    }

    public void create(Todo todo) {
        todo.setCreatedAt(LocalDateTime.now());
        repo.save(todo);
    }

    public void complete(Long id) {
        repo.findById(id).ifPresent(todo -> {
            todo.setCompleted(true);
            todo.setCompletedAt(LocalDateTime.now());
            todo.setUpdatedAt(LocalDateTime.now());
            repo.save(todo);
        });
    }

    public void revert(Long id) {
        repo.findById(id).ifPresent(todo -> {
            todo.setCompleted(false);
            todo.setUpdatedAt(LocalDateTime.now());
            repo.save(todo);
        });
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void save(Todo updated) {
        repo.findById(updated.getTodoId()).ifPresent(existing -> {
            existing.setDescription(updated.getDescription());
            existing.setDeadline(updated.getDeadline());
            existing.setPriority(updated.getPriority());
            existing.setProgress(updated.getProgress());
            existing.setNotes(updated.getNotes());
            existing.setUpdatedAt(LocalDateTime.now());
            repo.save(existing);
        });
    }
}
