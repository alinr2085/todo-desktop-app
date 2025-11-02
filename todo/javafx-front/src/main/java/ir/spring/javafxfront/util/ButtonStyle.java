package ir.spring.javafxfront.util;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ButtonStyle {

        public static DropShadow softShadow() {
                DropShadow shadow = new DropShadow();
                shadow.setRadius(8);
                shadow.setOffsetX(2);
                shadow.setOffsetY(2);
                shadow.setColor(Color.rgb(0, 0, 0, 0.25));
                return shadow;
        }

        public static void styleButton(javafx.scene.Node node) {
                node.setStyle("""
                -fx-background-color: linear-gradient(to right, #4facfe, #00f2fe);
                -fx-text-fill: black;
                -fx-font-weight: bold;
                -fx-font-size: 12px;
                -fx-background-radius: 10;
                -fx-cursor: hand;
        """);
                node.setEffect(softShadow());
        }


}
