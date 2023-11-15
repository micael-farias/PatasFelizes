package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import main.repositories.AnimalRepository;
import static main.utils.Constantes.DIALOG_CADASTRAR_ANIMAL;
import static main.utils.Constantes.DIALOG_CADASTRAR_VOLUNTARIO;

public class AdicionarVoluntarioController {

    @FXML
    private VBox cadastrarVoluntario;

    AnimalRepository animalRepository = new AnimalRepository();
            
    public void setOnClick(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
      
       cadastrarVoluntario.setOnMouseClicked(e -> {
            
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_VOLUNTARIO, contentFather, primmaryStage, blackShadow);
      });

     
    }
}