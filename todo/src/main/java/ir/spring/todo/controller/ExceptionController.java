package ir.spring.todo.controller;

import ir.spring.todo.dto.response.ExceptionResponse;
import ir.spring.todo.exception.TaskNotFound;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {
        private final MessageSource messageSource;

        public ExceptionController(MessageSource messageSource) {
                this.messageSource = messageSource;
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<List<ExceptionResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
                return ResponseEntity.status(400).body(getMethodArgumentNotValidExceptionResponse(ex));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<List<ExceptionResponse>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
                List<ExceptionResponse> exceptionResponses = new ArrayList<>();
                if (ex.getMessage().contains("TodoItemStatus")) {
                        exceptionResponses.add(ExceptionResponse.builder().
                                message(messageSource.getMessage("invalid_input_status", null, Locale.getDefault())).errorType("Status").build());
                }
                if (ex.getMessage().contains("LocalDateTime")) {
                        exceptionResponses.add(ExceptionResponse.builder().
                                message(messageSource.getMessage("invalid_input_deadline", null, Locale.getDefault())).errorType("deadline").build());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponses);
        }

        @ExceptionHandler(TaskNotFound.class)
        public ResponseEntity<List<ExceptionResponse>> handleTaskNotFound(TaskNotFound ex) {
                return ResponseEntity.status(400).body(Collections.singletonList(getTaskNotFoundExceptionResponse(ex)));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
                return ResponseEntity.status(400).body(getIllegalArgumentExceptionResponse(ex));
        }

        private List<ExceptionResponse> getMethodArgumentNotValidExceptionResponse(MethodArgumentNotValidException ex) {
                return ex.getFieldErrors().stream().map(
                        err -> ExceptionResponse.builder().message(err.getDefaultMessage()).errorType(err.getField()).build()).collect(Collectors.toList());
        }

        private ExceptionResponse getTaskNotFoundExceptionResponse(TaskNotFound ex) {
                return ExceptionResponse.builder().message(messageSource.getMessage(ex.getMessage(), null, Locale.getDefault())).errorType("TaskNotFound").build();
        }

        private ExceptionResponse getIllegalArgumentExceptionResponse(IllegalArgumentException ex) {
                return ExceptionResponse.builder().message(messageSource.getMessage(ex.getMessage(), null, Locale.getDefault())).errorType("InvalidInput").build();
        }


}
