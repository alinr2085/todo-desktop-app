package ir.spring.todo.controller;

import ir.spring.todo.dto.request.TodoItemRequest;
import ir.spring.todo.dto.response.ItemDetailsResponse;
import ir.spring.todo.dto.response.TodoItemResponse;
import ir.spring.todo.service.TodoItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo/{id}")
public class ActionOnItemController {
        private final TodoItemService todoItemService;

        public ActionOnItemController(TodoItemService todoItemService) {
                this.todoItemService = todoItemService;
        }

        @GetMapping("/details")
        public ResponseEntity<ItemDetailsResponse> getDetailsInfo(@PathVariable Long id) {
                return ResponseEntity.ok(todoItemService.getItemDetails(id));
        }

        @PatchMapping("/update")
        public ResponseEntity<TodoItemResponse> getUpdateInfo(@RequestBody @Valid TodoItemRequest todoItemRequest
                , @PathVariable Long id) {
                return ResponseEntity.ok(todoItemService.itemUpdate(todoItemRequest, id));
        }

        @DeleteMapping("/delete")
        public ResponseEntity<?> deleteInfo(@PathVariable Long id) {
                todoItemService.itemDelete(id);
                return ResponseEntity.ok().build();
        }


}
