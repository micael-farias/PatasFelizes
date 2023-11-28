package main.utils;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class TextFieldValidator {
    
    // Método para validar se um campo é diferente de nulo
    public static boolean validarNaoNulo(TextField textField, String mensagemErro) {
        String valor = textField.getText();

        if (valor == null || valor.trim().isEmpty()) {
            exibirMensagemErro(textField, mensagemErro);
            return false;
        } else {
            limparMensagemErro(textField);
            return true;
        }
    }

    // Método para aplicar validações a uma lista de text fields
    public static boolean validarCampos(List<TextField> campos) {
        for (TextField campo : campos) {
            if (!validarNaoNulo(campo, "Este campo não pode ser nulo.")) {
                return false;
            }
            // Adicione outras validações conforme necessário
        }
        return true;
    }

    // Método para exibir mensagem de erro
    private static void exibirMensagemErro(TextField textField, String mensagem) {
        // Lógica para exibir mensagem de erro (pode ser um label, borda vermelha, etc.)
        System.out.println("Erro: " + mensagem);
    }

    // Método para limpar mensagem de erro
    private static void limparMensagemErro(TextField textField) {
        // Lógica para limpar mensagem de erro (pode ser um label, remover borda vermelha, etc.)
        System.out.println("Limpar erro.");
    }

    public static void main(String[] args) {
        // Exemplo de uso:
        List<TextField> campos = new ArrayList<>();
        campos.add(new TextField());
        campos.add(new TextField());
        campos.add(new TextField());

        if (validarCampos(campos)) {
            System.out.println("Todos os campos são válidos!");
            // Lógica adicional quando todos os campos são válidos
        } else {
            System.out.println("Pelo menos um campo não é válido.");
        }
    }
}
