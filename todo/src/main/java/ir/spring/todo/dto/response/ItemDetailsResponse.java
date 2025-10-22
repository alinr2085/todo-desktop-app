package ir.spring.todo.dto.response;

import ir.spring.todo.enums.TodoItemStatus;
import lombok.Builder;

@Builder
public record ItemDetailsResponse (String title, String description, TodoItemStatus status, String createdAt, String updatedAt, String deadlineAt) {
}
