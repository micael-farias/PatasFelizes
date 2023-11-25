package main.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Tarefa;
import main.model.Voluntario;
import main.services.VoluntarioService;
import static main.utils.Constantes.PATH_IMAGES;
import main.views.gridview.EquipeGridView;

public class EquipeController implements Inicializador, Resumidor {

    @FXML
    private GridPane membrosGrid;
    @FXML
    private TextField textFieldBuscarVoluntario;
    @FXML
    private StackPane stackPaneScroll;
    
    private VoluntarioService voluntarioService;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize();
        criarGridVoluntarios(contentFather, primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        criarGridVoluntarios(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        voluntarioService = new VoluntarioService();
        hintBuscarVoluntario();
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        textFieldBuscarVoluntario.setOnKeyPressed(e ->{
            String nome = textFieldBuscarVoluntario.getText();
            if(e.getCode().equals(ENTER)){
                  List<Voluntario> voluntarios = voluntarioService.ObterVoluntarios(nome);
                  criarGridComResultados(voluntarios, contentFather, primmaryStage, blackShadow);
            }else if(e.getCode().equals(BACK_SPACE)){
                if(nome.length() == 0) hintBuscarVoluntario();
            }
        });  
    }
    
    public void criarGridVoluntarios(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        List<Voluntario> voluntarios = voluntarioService.ObterVoluntarios();         
        criarGridComResultados(voluntarios, contentFather, primmaryStage, blackShadow);
    }

    private void criarGridComResultados(List<Voluntario> voluntarios, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        EquipeGridView animalGridView = new EquipeGridView(membrosGrid, 4, voluntarios, contentFather, stackPaneScroll, primmaryStage, blackShadow);
        animalGridView.createGridAsync();  
    }

    private void hintBuscarVoluntario() {
        textFieldBuscarVoluntario.setPromptText("Procurar voluntário por nome");
    }
}
