package ir.spring.javafxfront.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.spring.javafxfront.config.ApiConfig;
import ir.spring.todo.dto.request.TodoItemRequest;
import ir.spring.todo.dto.response.ItemDetailsResponse;
import ir.spring.todo.dto.response.TodoItemResponse;
import ir.spring.todo.exception.TaskNotFound;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import java.net.http.HttpClient;


@RequiredArgsConstructor
public class TodoService {
        private HttpClient client;
        private final ObjectMapper mapper;

        public List<TodoItemResponse> getAllTasks() throws Exception {
                String getAll_url = ApiConfig.GET_API_URL;
                return jsonResponseMapToList(getAll_url);
        }

        public List<TodoItemResponse> getSortedTasks() throws Exception {
                String getSorted_url = ApiConfig.GET_API_URL + "/sort";
                return jsonResponseMapToList(getSorted_url);
        }

        public List<TodoItemResponse> getFilteredTasks() throws Exception {
                String getFiltered_url = ApiConfig.GET_API_URL + "/filter";
                return jsonResponseMapToList(getFiltered_url);
        }

        public ItemDetailsResponse getTaskDetails(Long id) {
                String getTask_details_url = ApiConfig.BASE_URL + id + "/details";
                try {
                        HttpResponse<String> response = getRequestAndSetResponseServer(getTask_details_url);
                        if (response.statusCode() == 200) {
                                return mapper.readValue(response.body(), ItemDetailsResponse.class);
                        } else {
                                throw new TaskNotFound("موردی بااین ایدی یافت نشد");
                        }
                } catch (IOException e) {
                        throw new RuntimeException("ارتباط با سرور برقرار نشد. لطفا بعدا دوباره تلاش کنید", e);
                } catch (InterruptedException e) {
                        throw new RuntimeException("درخواست لغو شد", e);
                }
        }

        public TodoItemResponse updateTask(Long id, TodoItemRequest objectRequest) {
                String updateTask_url = ApiConfig.BASE_URL + id + "/update";
                try {
                        String jsonRequest = ApiConfig.getMapper().writeValueAsString(objectRequest);
                        HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/json").
                                method("Patch", HttpRequest.BodyPublishers.ofString(jsonRequest)).
                                uri(URI.create(updateTask_url)).build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        if (response.statusCode() == 200) {
                                return mapper.readValue(response.body(), TodoItemResponse.class);
                        } else if (response.statusCode() == 404) {
                                throw new TaskNotFound("موردی بااین ایدی یافت نشد");
                        } else {
                                throw new RuntimeException("خطا در عملیات بروزرسانی رخ داده است. لطفا بعدا دوباره امتحان کنید");
                        }
                } catch (IOException e) {
                        throw new RuntimeException("ارتباط با سرور برقرار نشد. لطفا بعدا دوباره تلاش کنید", e);
                } catch (InterruptedException e) {
                        throw new RuntimeException("درخواست لغو شد", e);
                }
        }

        public boolean deleteTask(Long id) throws Exception {
                String deleteTask_url = ApiConfig.BASE_URL + id + "/delete";
                HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(deleteTask_url)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                        return true;
                } else if (response.statusCode() == 404) {
                        throw new TaskNotFound("موردی بااین ایدی یافت نشد");
                } else {
                        throw new RuntimeException("خطا در عملیات حذف رخ داده است. لطفا بعدا دوباره امتحان کنید");
                }
        }

        public TodoItemResponse newTask(TodoItemRequest objectRequest) {
                String newTask_url = ApiConfig.BASE_URL + "/new";
                try {
                        String jsonRequest = ApiConfig.getMapper().writeValueAsString(objectRequest);
                        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest
                                .BodyPublishers.ofString(jsonRequest)).uri(URI.create(newTask_url)).build();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        if (response.statusCode() == 200) {
                                return mapper.readValue(response.body(), TodoItemResponse.class);
                        } else {
                                throw new RuntimeException("خطا در عملیات ساخت مورد رخ داده است. لطفا بعدا دوباره امتحان کنید");
                        }
                } catch (IOException e) {
                        throw new RuntimeException("ارتباط با سرور برقرار نشد. لطفا بعدا دوباره تلاش کنید", e);
                } catch (InterruptedException e) {
                        throw new RuntimeException("درخواست لغو شد", e);
                }
        }

        private List<TodoItemResponse> jsonResponseMapToList(String url) throws Exception {
                return mapper.readValue(getRequestAndSetResponseServer(url).body(), new TypeReference<>() {
                });
        }

        private HttpResponse<String> getRequestAndSetResponseServer(String url) throws IOException, InterruptedException {
                HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
                return client.send(request, HttpResponse.BodyHandlers.ofString());
        }


}
