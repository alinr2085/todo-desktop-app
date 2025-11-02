package ir.spring.javafxfront.controller;

import ir.spring.javafxfront.util.Animation;
import ir.spring.javafxfront.util.ButtonStyle;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WelcomeAndFirstPageController {
        public void welcomePage(Stage stage) {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("ir/spring/javafxfront/images/welcome.jpg")).toExternalForm());
                ImageView welcomeBackground = new ImageView(image);
                welcomeBackground.setFitWidth(1200);
                welcomeBackground.setFitHeight(700);
                welcomeBackground.setPreserveRatio(false);

                Text welcomeText = new Text();
                welcomeText.setFont(Font.font("Arial", 15));
                welcomeText.setFill(Color.BLACK);

                StackPane root = new StackPane(welcomeBackground, welcomeText);
                Scene scene = new Scene(root, 1200, 700);
                stage.setScene(scene);
                stage.setTitle("Welcome");
                stage.show();

                StackPane.setAlignment(welcomeText, Pos.CENTER);
                Animation.textAnimation("Welcome to Todo App - Prod by Ali", welcomeText);
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(e -> firstPage(stage));
                delay.play();
        }

        private void firstPage(Stage stage) {
                Button newTaskButton = new Button("New Task"), showTaskButton = new Button("Show Tasks");
                Image image = new Image(Objects.requireNonNull(getClass().getResource("ir/spring/javafxfront/images/background.jpg")).toExternalForm());
                ImageView background = new ImageView(image);
                background.setFitWidth(1200);
                background.setFitHeight(700);
                background.setPreserveRatio(false);
                ButtonStyle.styleButton(newTaskButton);
                ButtonStyle.styleButton(showTaskButton);
                Animation.buttonAnimation(newTaskButton);
                Animation.buttonAnimation(showTaskButton);

                StackPane root = new StackPane(background, newTaskButton, showTaskButton);
                StackPane.setAlignment(newTaskButton, Pos.BOTTOM_LEFT);
                StackPane.setAlignment(showTaskButton, Pos.BOTTOM_RIGHT);
                Scene scene = new Scene(root, 1200, 700);
                stage.setScene(scene);
                stage.setTitle("Choice Button");
                stage.show();
        }
}
