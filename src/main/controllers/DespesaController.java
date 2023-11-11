package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.interfaces.Inicializador;

public class DespesaController implements Inicializador{

    @FXML
    private HBox layoutDespesa;

    public void setData(int posicao) {
        if(posicao % 2 == 0){
            layoutDespesa.setStyle("-fx-background-color: gray;");
        }      
    }

    @Override
    public void Inicializar(Pane contentFather, Pane blackShadow) {

    }  
}
