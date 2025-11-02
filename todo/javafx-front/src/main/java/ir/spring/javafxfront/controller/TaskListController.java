package ir.spring.javafxfront.controller;

import ir.spring.javafxfront.view.TaskBox;
import ir.spring.todo.dto.response.TodoItemResponse;
import ir.spring.javafxfront.util.Animation;
import ir.spring.javafxfront.util.ButtonStyle;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskListController {

        public void showTaskList(Stage stage) {
                //private final TodoService todoService = new TodoService();
                ListView<TodoItemResponse> taskList = new ListView<>();

                taskList.setCellFactory(task -> new ListCell<>(){
                        @Override
                        protected void updateItem(TodoItemResponse response, boolean empty){
                                super.updateItem(response, empty);
                                if(empty || response == null) {
                                        setGraphic(null);
                                }else {
                                        TaskBox taskBox = new TaskBox(response);
                                        setGraphic(taskBox);
                                }
                        }
                });

                BorderPane root = new BorderPane();
                root.setTop(addFilterAndSortToPage());
                root.setCenter(taskList);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Task List");
                stage.show();
        }

        private HBox addFilterAndSortToPage() {
                HBox topBar = new HBox(20);
                topBar.setPadding(new Insets(15));
                topBar.setStyle("-fx-background-color: #f0f0f0;");
                topBar.getChildren().addAll(filterButton(), sortButton());
                return topBar;        }

        private VBox sortButton() {
                ComboBox<String> sortComboBox = new ComboBox<>();
                sortComboBox.getItems().addAll("CreatedAt", "UpdatedAt", "DeadlineAt", "Not sorted");
                sortComboBox.setValue("Not sorted");
                sortComboBox.setOnAction(event -> {
                });

                VBox sortVBox = new VBox(10, new Label("Sort by"), sortComboBox);
                sortVBox.setPadding(new Insets(20));
                Animation.buttonAnimation(sortVBox);
                ButtonStyle.styleButton(sortComboBox);
                return sortVBox;
        }

        private Button filterButton() {
                Button filterButton = new Button("Filter");
                ButtonStyle.styleButton(filterButton);
                Animation.buttonAnimation(filterButton);
                return filterButton;
        }

}
