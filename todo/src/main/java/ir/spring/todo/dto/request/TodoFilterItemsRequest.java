package ir.spring.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.spring.todo.enums.TodoItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoFilterItemsRequest {
        private TodoItemStatus status;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private List<LocalDateTime> deadline;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private List<LocalDateTime> create;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private List<LocalDateTime> update;
}
