package main.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.model.Tarefa;
import main.services.TarefaServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_TAREFA;
import main.views.gridview.TarefasGridView;

public class TarefasController implements Inicializador {  

    @FXML
    private Button novaTarefa;

    @FXML
    private GridPane tarefasGrid;
    
    @FXML
    private StackPane stackPaneScroll;

    private TarefaServices tarefaService;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) { 
        initialize();
        initializeViews(contentFather);
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        tarefaService = new TarefaServices();
    }
    
    public void initializeViews(Pane contentFather){
        List<Tarefa> tarefas = tarefaService.ObterTarefas();
        TarefasGridView animalGridView = new TarefasGridView(tarefasGrid, 1, tarefas, contentFather, stackPaneScroll);
        animalGridView.createGridAsync();        
    }
  
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
         novaTarefa.setOnMouseClicked(e->{
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_TAREFA, contentFather, primmaryStage, blackShadow);
        });   
    }
}
