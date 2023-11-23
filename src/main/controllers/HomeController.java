package main.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Animal;
import main.repositories.AnimalRepository;
import static main.utils.Constantes.DIALOG_FILTRAR_ANIMAL;
import main.utils.EmailSenderThread;
import main.views.gridview.AnimalGridView;

public class HomeController implements Inicializador, Resumidor{
    
    

    @FXML
    private StackPane stackPaneScroll;
    
    @FXML
    private Button filtrarAnimaisButton;
    
    @FXML
    private GridPane animaisGrid;
    
    private AnimalRepository repository;
 
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize();
        criarGridAnimais(contentFather, primmaryStage, blackShadow);
        setListeners(contentFather, blackShadow);
    }
    
    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados){
        criarGridAnimais(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        this.repository = new AnimalRepository();
    }
    
    public void setListeners(Pane contentFather, Pane blackShadow){
        filtrarAnimaisButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogAlinhado(DIALOG_FILTRAR_ANIMAL, contentFather, filtrarAnimaisButton, blackShadow);
        });
    }
    
    public void criarGridAnimais(Pane contentFather, Stage primmaryStage, Pane blackShadow){       
        List<Animal> animais = repository.EncontrarAnimais();
        AnimalGridView animalGridView = new AnimalGridView(animaisGrid, 5, animais, contentFather, primmaryStage, blackShadow, stackPaneScroll);
        animalGridView.createGridAsync();
    }
}
