package ir.spring.todo.service;

import ir.spring.todo.dto.request.TodoFilterItemsRequest;
import ir.spring.todo.dto.request.TodoItemRequest;
import ir.spring.todo.dto.response.ItemDetailsResponse;
import ir.spring.todo.dto.response.TodoItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoItemService {

        Page<TodoItemResponse> getAllItems(Pageable pageable);
        TodoItemResponse addNewItem(TodoItemRequest request);
        ItemDetailsResponse getItemDetails(Long id);
        Page<TodoItemResponse> getSortedItems(Pageable pageable, String sortBy);
        Page<TodoItemResponse> getFilteredItems(Pageable pageable, TodoFilterItemsRequest request);
        TodoItemResponse itemUpdate(TodoItemRequest request, Long id);
        void itemDelete(Long id);
}
