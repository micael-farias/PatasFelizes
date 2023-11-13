package main.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Animal;
import main.repositories.AnimalRepository;
import static main.utils.Constantes.DIALOG_FILTRAR_ANIMAL;
import main.views.gridview.AnimalGridView;

public class HomeController implements Inicializador, Resumidor{
    
    
    @FXML
    private Button filtrarAnimaisButton;
    
    @FXML
    private GridPane animaisGrid;
    AnimalRepository repository = new AnimalRepository();

 
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        repository.adicionarAnimais();
        List<Animal> animais = repository.getAnimais();
        AnimalGridView animalGridView = new AnimalGridView(animaisGrid, 5, animais, contentFather, primmaryStage,blackShadow);
        animalGridView.createGridAsync();
        
        filtrarAnimaisButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogAlinhado(DIALOG_FILTRAR_ANIMAL, contentFather, filtrarAnimaisButton, blackShadow);
        });
    }
    
    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        List<Animal> animais = repository.getAnimais();
        AnimalGridView animalGridView = new AnimalGridView(animaisGrid, 5, animais, contentFather, primmaryStage, blackShadow);
    }
}
