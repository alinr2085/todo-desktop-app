package ir.spring.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.spring.todo.enums.TodoItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemRequest {
        public interface OnCreate {
        }

        @NotNull(groups = OnCreate.class, message = "{title_is_null}")
        @NotBlank(groups = OnCreate.class, message = "{title_is_blank}")
        private String title;
        @NotNull(groups = OnCreate.class, message = "{description_is_null}")
        @NotBlank(groups = OnCreate.class, message = "{description_is_blank}")
        private String description;
        @NotNull(groups = OnCreate.class, message = "{deadline_is_null}")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime deadline;
        private TodoItemStatus status;
}
