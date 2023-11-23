package main.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ENTER;
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
import main.views.gridview.AnimalGridView;

public class HomeController implements Inicializador, Resumidor{    

    @FXML
    private TextField buscarAnimalTextField;
    
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
        setListeners(contentFather, blackShadow, primmaryStage);
    }
    
    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados){
        hintBuscarAnimais();
        criarGridAnimais(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        this.repository = new AnimalRepository();
        hintBuscarAnimais();
    }
    
    public void hintBuscarAnimais(){
        buscarAnimalTextField.setPromptText("Procurar animal por nome");
    }
    
    public void setListeners(Pane contentFather, Pane blackShadow, Stage primmaryStage){
        filtrarAnimaisButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogAlinhado(DIALOG_FILTRAR_ANIMAL, contentFather, filtrarAnimaisButton, blackShadow);
        });
        
        buscarAnimalTextField.setOnKeyPressed(e ->{
            String animal = buscarAnimalTextField.getText();
            if(e.getCode().equals(ENTER)){
                  List<Animal> animais = repository.EncontrarAnimaisPorNome(animal);
                  criarGridComResultados(animais, contentFather, primmaryStage, blackShadow);
            }else if(e.getCode().equals(BACK_SPACE)){
                if(animal.length() == 0) hintBuscarAnimais();
            }
        });
        
        
    }
    
    public void criarGridAnimais(Pane contentFather, Stage primmaryStage, Pane blackShadow){       
        List<Animal> animais = repository.EncontrarAnimais();
        criarGridComResultados(animais, contentFather, primmaryStage, blackShadow);
    }
    
    public void criarGridComResultados(List<Animal> itens, Pane contentFather, Stage primmaryStage, Pane blackShadow){
        AnimalGridView animalGridView = new AnimalGridView(animaisGrid, 4, itens, contentFather, primmaryStage, blackShadow, stackPaneScroll);
        animalGridView.createGridAsync();    
    }
}
