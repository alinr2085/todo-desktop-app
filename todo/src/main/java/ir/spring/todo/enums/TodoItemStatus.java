package ir.spring.todo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TodoItemStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED;

        @JsonCreator
        public static TodoItemStatus getValidStatus(String value) {
                for (TodoItemStatus status : TodoItemStatus.values()) {
                        if (status.toString().equalsIgnoreCase(value)) return status;
                }
                throw new IllegalArgumentException();
        }
}
