package ir.spring.todo.controller;

import ir.spring.todo.dto.request.TodoItemRequest;
import ir.spring.todo.dto.response.TodoItemResponse;
import ir.spring.todo.service.TodoItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoItemController {
        private final TodoItemService todoItemService;

        public TodoItemController(TodoItemService todoItemService) {
                this.todoItemService = todoItemService;
        }

        @PostMapping("/new")
        public ResponseEntity<TodoItemResponse> addNewItem(@RequestBody @Validated(TodoItemRequest.OnCreate.class) TodoItemRequest request) {
                return ResponseEntity.ok(todoItemService.addNewItem(request));
        }




}
