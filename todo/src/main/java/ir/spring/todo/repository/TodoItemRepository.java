package ir.spring.todo.repository;

import ir.spring.todo.enums.TodoItemStatus;
import ir.spring.todo.model.TodoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

        Page<TodoItem> findByStatus(Pageable pageable, TodoItemStatus status);

        Page<TodoItem> findByCreatedAtBetween(Pageable pageable, LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);

        Page<TodoItem> findByUpdatedAtBetween(Pageable pageable, LocalDateTime updatedAtAfter, LocalDateTime updatedAtBefore);

        Page<TodoItem> findByDeadlineAtBetween(Pageable pageable, LocalDateTime deadlineAtAfter, LocalDateTime deadlineAtBefore);
}
