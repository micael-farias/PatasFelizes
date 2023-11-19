package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import static main.utils.Constantes.DIALOG_CADASTRAR_ANIMAL;

public class AdicionarAnimalController {

    @FXML
    private VBox adicionarPet;
            
    public void setOnClick(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
      
       adicionarPet.setOnMouseClicked(e -> {
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_ANIMAL, contentFather, primmaryStage, blackShadow);
      });  
    }
}