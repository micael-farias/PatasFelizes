package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.interfaces.InicializadorComDado;

public class AnimalDetalhesController implements InicializadorComDado{
    
    @FXML
    private Label nomeAnimal;
    
    @Override
    public void Inicializar(Pane contentFather, Object dado) {
        String nome = dado.toString();
        nomeAnimal.setText(nome);
    }
}
