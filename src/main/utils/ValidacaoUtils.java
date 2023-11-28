package main.utils;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.NumberStringConverter;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import static main.utils.DateHelper.invalidString;

public class ValidacaoUtils {

   
    public static boolean validarCampo(String valor, TextInputControl inputControl, String mensagemErro) {
        if (invalidString(valor)) {
            exibirErro(inputControl, mensagemErro);
            return false;
        } else {
            System.out.println("lim");
            limparErro(inputControl);
        }
        return true;
    }
    
    public static boolean validarCampoReal(double valor, TextInputControl inputControl, String mensagemErro) {
        if (!(valor > 0)){
            exibirErro(inputControl, mensagemErro);
            return false;
        } else {
            System.out.println("lim");
            limparErro(inputControl);
        }
        return true;
    }

    public static void exibirErro(TextInputControl inputControl, String mensagemErro) {
        inputControl.setPromptText(mensagemErro);
        inputControl.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
    }

    public static void limparErro(TextInputControl inputControl) {
        inputControl.setPromptText("");
        inputControl.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
    }

    public static boolean validarCampo(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            datePicker.setPromptText("A data não pode ser vazia");
            findTextField(datePicker).setStyle(datePicker.getStyle() + "-fx-border-color: red ; -fx-border-width: 1px ;");
            return false;
        } else {
            datePicker.setPromptText("");
           findTextField(datePicker).setStyle(datePicker.getStyle() + "-fx-border-color: black ; -fx-border-width: 1px ;");
        }
        return true;
    }
    
       private static TextField findTextField(DatePicker datePicker) {
        for (Node node : datePicker.getEditor().lookupAll(".text-field")) {
            if (node instanceof TextField) {
                return (TextField) node;
            }
        }
        return null;
    }
       
    public static boolean isValidEmailAddress(String email) {
   boolean result = true;
   try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
   } catch (AddressException ex) {
      result = false;
   }
   return result;
}

    
    public static void mascaraEmail(TextField textField){

        textField.setOnKeyTyped((KeyEvent event) -> {
            if("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@".contains(event.getCharacter())==false){
                event.consume();
            }

            if("@".equals(event.getCharacter())&&textField.getText().contains("@")){
                event.consume();
            }

            if("@".equals(event.getCharacter())&&textField.getText().length()==0){
                event.consume();
            }
        });

    }
              



}
