package ir.spring.javafxfront.view;

import ir.spring.todo.dto.response.TodoItemResponse;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaskBox extends HBox {
        private Button description = new Button("Description");
        private Button update = new Button("Update");
        private Button delete = new Button("Delete");
        private Label title;
        private Label id;
        private Label status;

        public TaskBox(TodoItemResponse todoItemResponse) {
                this.id = new Label(String.valueOf(todoItemResponse.id()));
                this.title = new Label(todoItemResponse.title());
                this.status = new Label(String.valueOf(todoItemResponse.status()));

                HBox choiceVbox = new HBox(description, update, delete);
                choiceVbox.setSpacing(5);

                VBox taskInfo = new VBox(id, title, status);
                taskInfo.setSpacing(5);

                this.getChildren().addAll(choiceVbox, taskInfo);
                this.setSpacing(10);
                this.setPadding(new Insets(10));


        }

}
