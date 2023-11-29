package main.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class FiltroAplicadoController  {

    @FXML
    private Label filtroAplicado;
    
    
    public void setData(String nome){
        
        filtroAplicado.setText(nome);
    }

}