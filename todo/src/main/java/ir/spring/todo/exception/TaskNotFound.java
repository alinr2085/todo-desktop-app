package ir.spring.todo.exception;

public class TaskNotFound extends RuntimeException {
        public TaskNotFound(String message) {
                super(message);
        }
}
