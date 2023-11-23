package main.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Voluntario;
import main.services.VoluntarioService;
import static main.utils.Constantes.PATH_IMAGES;
import main.views.gridview.EquipeGridView;

public class EquipeController implements Inicializador, Resumidor {

    @FXML
    private GridPane membrosGrid;
    
    @FXML
    private StackPane stackPaneScroll;
    
    private VoluntarioService voluntarioService;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize();
        criarGridVoluntarios(contentFather, primmaryStage, blackShadow);
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        criarGridVoluntarios(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        voluntarioService = new VoluntarioService();
    }
    
    public void criarGridVoluntarios(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        List<Voluntario> voluntarios = voluntarioService.ObterVoluntarios();         
        EquipeGridView animalGridView = new EquipeGridView(membrosGrid, 3, voluntarios, contentFather, stackPaneScroll, primmaryStage, blackShadow);
        animalGridView.createGridAsync();   
    }
}
