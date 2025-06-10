package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String showTodos(Model model) {
        model.addAttribute("todos", service.getAll());
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("todo", new Todo());
        return "index";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Todo todo) {
        service.create(todo);
        return "redirect:/";
    }

    @PostMapping("/complete")
    public String complete(@RequestParam Long todoId) {
        service.complete(todoId);
        return "redirect:/";
    }

    @PostMapping("/revert")
    public String revert(@RequestParam Long todoId) {
        service.revert(todoId);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long todoId) {
        service.delete(todoId);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Todo todo) {
        service.save(todo);
        return "redirect:/";
    }
}
