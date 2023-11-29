package main.utils;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;

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

    public static String formatarTelefone(String numero) {
        if (numero == null) {
            return "";
        }

        // Remover todos os caracteres não numéricos
        String numerosApenas = numero.replaceAll("\\D", "");

        // Verificar o tamanho da string
           // Verificar o tamanho da string
        if (numerosApenas.length() == 11) {
            // Formato (##) 9 ####-####
            return numerosApenas.replaceFirst("(\\d{2})(\\d{1})(\\d{4})(\\d{4})", "($1) $2 $3-$4");
        } else if (numerosApenas.length() == 10) {
            // Formato (##) ####-####
            return numerosApenas.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        } else {
            // Caso não corresponda aos formatos esperados
            return numero;
        }
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
    
    public static void autoCapitalizeFirstLetter(TextInputControl textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                textField.setText(newValue.substring(0, 1).toUpperCase() + newValue.substring(1));
            }
        });
    }
    
       public static void capitalizeEachWord(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }

            StringBuilder result = new StringBuilder();
            boolean capitalizeNext = true;

            for (char c : newValue.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    result.append(c);
                    capitalizeNext = true;
                } else {
                    if (capitalizeNext) {
                        result.append(Character.toUpperCase(c));
                        capitalizeNext = false;
                    } else {
                        result.append(c);
                    }
                }
            }

            textField.setText(result.toString());
        });
    }
    
      public static void applyNumericMask(TextField textField) {
        // Configura um TextFormatter para aceitar apenas números
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textField.setTextFormatter(textFormatter);
    }
}
