package main.utils;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.Locale;

public class TextFieldUtils {

    public static void setupCurrencyTextField(TextField textField) {
        setNodeOrientation(textField);

        SimpleDoubleProperty amount = new SimpleDoubleProperty(textField, "amount", 0.00);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        textField.setText(format.format(amount.get()));

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                int length = textField.getText().length();
                textField.selectRange(length, length);
                textField.positionCaret(length);
            });
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            formatText(textField, format, amount, newValue);
        });
    }

    private static void setNodeOrientation(TextField textField) {
        textField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }

    private static void formatText(TextField textField, NumberFormat format, SimpleDoubleProperty amount, String text) {
        if (text != null && !text.isEmpty()) {
            String plainText = text.replaceAll("[^0-9]", "");

            while (plainText.length() < 3) {
                plainText = "0" + plainText;
            }

            StringBuilder builder = new StringBuilder(plainText);
            builder.insert(plainText.length() - 2, ".");

            Double newValue = Double.parseDouble(builder.toString());
            amount.set(newValue);
            textField.setText(format.format(newValue));
        }
    }
}
