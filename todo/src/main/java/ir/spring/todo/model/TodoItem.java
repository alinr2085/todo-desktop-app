package ir.spring.todo.model;

import ir.spring.todo.enums.TodoItemStatus;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(schema = "todo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "update todo_schema.todo_item set deleted_at = now() where id = ?")
public class TodoItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title;
        private String description;
        @Enumerated(EnumType.STRING)
        private TodoItemStatus status;
        @CreationTimestamp
        @Column(updatable = false)
        private LocalDateTime createdAt;
        @UpdateTimestamp
        private LocalDateTime updatedAt;
        private LocalDateTime deadlineAt;
        private LocalDateTime deletedAt;
}