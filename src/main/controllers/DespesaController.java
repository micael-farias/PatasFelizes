package main.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    public void Inicializar(Pane contentFather) {

    }
  
    
}
