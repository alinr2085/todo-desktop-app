package ir.spring.todo.dto.response;

import ir.spring.todo.enums.TodoItemStatus;
import lombok.Builder;

@Builder
public record TodoItemResponse(Long id, String title, TodoItemStatus status) {
}
