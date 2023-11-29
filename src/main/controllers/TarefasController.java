package main.controllers;

import java.util.List;
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
import main.model.Despesa;
import main.model.FiltroDespesa;
import main.model.Procedimento;
import main.model.Tarefa;
import main.services.TarefaServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_TAREFA;
import static main.utils.Constantes.DIALOG_FILTRAR_DESPESAS;
import static main.utils.Constantes.DIALOG_FILTRAR_TAREFAS;
import static main.utils.Constantes.FORM_DESPESAS;
import main.views.gridview.TarefasGridView;

public class TarefasController implements Inicializador , Resumidor{  

    @FXML
    private GridPane tarefasGrid;
    
    @FXML
    private StackPane stackPaneScroll;

    @FXML
    private TextField textFieldBuscarTarefa;
    @FXML
    private Button filtrarTarefas;
    private TarefaServices tarefaService;
    
    private static FiltroDespesa filtro;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) { 
        initialize();
        initializeViews(contentFather,primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        tarefaService = new TarefaServices();
    }
    
    public void initializeViews(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        if(filtro != null){
            var despesas = tarefaService.FiltrarTarefas(filtro);    
            criarGridComResultados(despesas, contentFather, primmaryStage, blackShadow);   
        }else{
           List<Procedimento> tarefas = tarefaService.ObterTarefas();
           criarGridComResultados(tarefas, contentFather, primmaryStage, blackShadow);
        }
    }
  
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){

        textFieldBuscarTarefa.setOnKeyPressed(e ->{
            String tarefa = textFieldBuscarTarefa.getText();
            if(e.getCode().equals(ENTER)){
                  List<Procedimento> tarefas = tarefaService.EncontrarTarefasPorDescricao(tarefa);
                  criarGridComResultados(tarefas, contentFather, primmaryStage, blackShadow);
            }
        });           
        
        filtrarTarefas.setOnMouseClicked(e -> {
            App.getInstance().AbrirDialogComAcao(DIALOG_FILTRAR_TAREFAS, FORM_DESPESAS, contentFather, primmaryStage, blackShadow, null, (dados) ->{
                List<Procedimento> procedimentos = (List<Procedimento>)dados[0];
                filtro = (FiltroDespesa) dados[1];
                criarGridComResultados(procedimentos,contentFather, primmaryStage, blackShadow);
            });
            
        });     
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        initializeViews(contentFather,primmaryStage, blackShadow);
    }

    private void criarGridComResultados(List<Procedimento> tarefas, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        TarefasGridView animalGridView = new TarefasGridView(contentFather, primmaryStage, blackShadow, tarefasGrid, 1, tarefas, stackPaneScroll);
        animalGridView.createGridAsync();  
    }
}
