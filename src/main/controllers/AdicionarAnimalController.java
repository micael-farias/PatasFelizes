package main.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.repositories.AnimalRepository;
import static main.utils.Constantes.FORM_BASE;

public class AdicionarAnimalController {

    @FXML
    private VBox adicionarPet;
    AnimalRepository animalRepository = new AnimalRepository();
            
    public void setOnClick() {
      
        adicionarPet.setOnMouseClicked(e ->{
                animalRepository.adicionarAnimal();
//            try {
//                Stage dialog = new Stage();
//                dialog.initStyle(StageStyle.UTILITY);
//                var resource2 = getClass().getResource("../views/fxml/CadastrarAnimal.fxml");
//                Parent root2 = FXMLLoader.load(resource2);
//                Scene scene2 = new Scene(root2);
//                dialog.setScene(scene2);  
//                dialog.show();
//            } catch (IOException ex) {
//                Logger.getLogger(AdicionarAnimalController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        });
     
    }
}