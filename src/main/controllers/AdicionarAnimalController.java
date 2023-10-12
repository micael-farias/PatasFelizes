package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class AdicionarAnimalController {

    @FXML
    private VBox adicionarPet;
            
    public void setOnClick() {
      
        adicionarPet.setOnMouseClicked(e ->{
        
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Oi eu sou  me adote");
            alert.showAndWait();   
        });
     
    }
}