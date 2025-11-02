package ir.spring.javafxfront.util;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Animation {

        public static void buttonAnimation(javafx.scene.Node node) {
                node.setOnMousePressed(e -> {
                        node.setScaleX(0.95);
                        node.setScaleY(0.95);
                });

                node.setOnMouseReleased(e -> {
                        node.setScaleX(1.0);
                        node.setScaleY(1.0);
                });
        }

        public static void textAnimation(String message, Text text) {
                Duration typingSpeed = Duration.millis(120);
                SequentialTransition typingAnimation = new SequentialTransition();
                for (int i = 0; i < message.length(); i++) {
                        final int index = i;
                        KeyFrame frame = new KeyFrame(
                                typingSpeed.multiply(i),
                                e -> text.setText(message.substring(0, index + 1))
                        );
                        typingAnimation.getChildren().add(new Timeline(frame));
                }
                typingAnimation.play();
        }

}
