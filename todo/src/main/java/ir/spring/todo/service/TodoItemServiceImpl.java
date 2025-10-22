package ir.spring.todo.service;

import ir.spring.todo.dto.request.TodoFilterItemsRequest;
import ir.spring.todo.dto.request.TodoItemRequest;
import ir.spring.todo.dto.response.ItemDetailsResponse;
import ir.spring.todo.dto.response.TodoItemResponse;
import ir.spring.todo.enums.TodoItemStatus;
import ir.spring.todo.exception.TaskNotFound;
import ir.spring.todo.model.TodoItem;
import ir.spring.todo.repository.TodoItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TodoItemServiceImpl implements ir.spring.todo.service.TodoItemService {
        private final TodoItemRepository todoItemRepository;

        public TodoItemServiceImpl(TodoItemRepository todoItemRepository) {
                this.todoItemRepository = todoItemRepository;
        }

        @Override
        public Page<TodoItemResponse> getAllItems(Pageable pageable) {
                return todoItemRepository.findAll(pageable).map(this::itemMapToItemResponse);
        }

        @Override
        public TodoItemResponse addNewItem(TodoItemRequest request) {
                TodoItem todoItem = itemRequestMapToItem(request);
                if (request.getStatus() == null) {
                        request.setStatus(TodoItemStatus.NOT_STARTED);
                }
                return itemMapToItemResponse(todoItemRepository.save(todoItem));
        }

        @Override
        public ItemDetailsResponse getItemDetails(Long id) {
                TodoItem item = todoItemRepository.findById(id).orElseThrow(() -> new TaskNotFound("task_not_found"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                return ItemDetailsResponse.builder().title(item.getTitle()).description(item.getDescription()).status(item.getStatus()).
                        createdAt(item.getCreatedAt().format(formatter)).updatedAt(item.getUpdatedAt().format(formatter)).
                        deadlineAt(item.getDeadlineAt().format(formatter)).build();
        }

        @Override
        public Page<TodoItemResponse> getSortedItems(Pageable pageable, String sortBy) {
                Pageable sortedItems = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, sortBy)
                );
                return todoItemRepository.findAll(sortedItems).map(this::itemMapToItemResponse);
        }

        @Override
        public Page<TodoItemResponse> getFilteredItems(Pageable pageable, TodoFilterItemsRequest request) {
                Page<TodoItem> filteredItems = Page.empty();
                if (request.getCreate() != null)
                        filteredItems = todoItemRepository.findByCreatedAtBetween(pageable, request.getCreate().get(0), request.getCreate().get(1));
                if (request.getUpdate() != null)
                        filteredItems = todoItemRepository.findByUpdatedAtBetween(pageable, request.getUpdate().get(0), request.getUpdate().get(1));
                if (request.getDeadline() != null)
                        filteredItems = todoItemRepository.findByDeadlineAtBetween(pageable, request.getDeadline().get(0), request.getDeadline().get(1));
                if (request.getStatus() != null)
                        filteredItems = todoItemRepository.findByStatus(pageable, request.getStatus());
                return filteredItems.map(this::itemMapToItemResponse);
        }

        @Override
        public TodoItemResponse itemUpdate(TodoItemRequest request, Long id) {
                TodoItem todoItem = todoItemRepository.findById(id).orElseThrow(() -> new TaskNotFound("task_not_found"));
                boolean isUpdated = false;
                if (request.getStatus() != null) {
                        todoItem.setStatus(request.getStatus());
                        isUpdated = true;
                }
                if (request.getDeadline() != null) {
                        todoItem.setDeadlineAt(request.getDeadline());
                        isUpdated = true;
                }
                if (request.getTitle() != null) {
                        todoItem.setTitle(request.getTitle());
                        isUpdated = true;
                }
                if (request.getDescription() != null) {
                        todoItem.setDescription(request.getDescription());
                        isUpdated = true;
                }
                if (isUpdated) {
                        todoItem.setUpdatedAt(LocalDateTime.now());
                        return itemMapToItemResponse(todoItemRepository.save(todoItem));
                } else return itemMapToItemResponse(todoItem);
        }

        @Override
        @Transactional
        public void itemDelete(Long id) {
                todoItemRepository.delete(todoItemRepository.findById(id).orElseThrow(() -> new TaskNotFound("task_not_found")));
        }

        private TodoItemResponse itemMapToItemResponse(TodoItem todoItem) {
                return TodoItemResponse.builder().id(todoItem.getId()).title(todoItem.getTitle()).status(todoItem.getStatus()).build();
        }

        private TodoItem itemRequestMapToItem(TodoItemRequest request) {
                return TodoItem.builder().title(request.getTitle()).description(request.getDescription()).
                        status(request.getStatus()).deadlineAt(request.getDeadline()).build();
        }


}
