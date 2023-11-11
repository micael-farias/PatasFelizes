package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import main.repositories.AnimalRepository;
import static main.utils.Constantes.DIALOG_CADASTRAR_ANIMAL;

public class AdicionarAnimalController {

    @FXML
    private VBox adicionarPet;
    AnimalRepository animalRepository = new AnimalRepository();
            
    public void setOnClick(Pane contentFather, Pane blackShadow) {
      
       adicionarPet.setOnMouseClicked(e -> {
            animalRepository.adicionarAnimal();
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_ANIMAL, contentFather, blackShadow);
      });

     
    }
}