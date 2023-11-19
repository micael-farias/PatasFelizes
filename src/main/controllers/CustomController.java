package main.controllers;

import javafx.scene.control.TextField;

public class CustomController {
    
    public Object ObterDadoArray(Object[] dados, int posicao){
        try{
            return dados[posicao];
        }catch(Exception ex){
            return null;
        }    
    }
    
    public void removerUltimoDigito(TextField textField) {
        String textoAtual = textField.getText();

        if (!textoAtual.isEmpty()) {
            // Remove o último caractere
            String novoTexto = textoAtual.substring(0, textoAtual.length() - 1);
            textField.setText(novoTexto);
        }
    }
}
