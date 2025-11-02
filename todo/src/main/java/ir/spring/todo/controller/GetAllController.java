package ir.spring.todo.controller;

import ir.spring.todo.dto.request.TodoFilterItemsRequest;
import ir.spring.todo.dto.response.TodoItemResponse;
import ir.spring.todo.service.TodoItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/get_all")
public class GetAllController {
        private final TodoItemService todoItemService;

        public GetAllController(TodoItemService todoItemService) {
                this.todoItemService = todoItemService;
        }

        @GetMapping
        public ResponseEntity<Page<TodoItemResponse>> getAllItems(Pageable pageable) {
                return ResponseEntity.ok(todoItemService.getAllItems(pageable));
        }

        @GetMapping("/sort")
        public ResponseEntity<Page<TodoItemResponse>> getSortedItems(Pageable pageable, @RequestParam String sortBy) {
                List<String> sortByListed = List.of("createdAt", "updatedAt", "deadlineAt");
                if (!sortByListed.contains(sortBy)) {
                        throw new IllegalArgumentException("invalid_sort_type");
                }
                return ResponseEntity.ok(todoItemService.getSortedItems(pageable, sortBy));
        }

        @PostMapping("/filter")
        public ResponseEntity<Page<TodoItemResponse>> getFilteredItems(Pageable pageable, @RequestBody TodoFilterItemsRequest filterRequest) {
                if (filterRequest.getCreate() == null && filterRequest.getUpdate() == null &&
                        filterRequest.getDeadline() == null && filterRequest.getStatus() == null) {
                        throw new IllegalArgumentException("empty_filter_request");
                }
                return ResponseEntity.ok(todoItemService.getFilteredItems(pageable, filterRequest));
        }
}
