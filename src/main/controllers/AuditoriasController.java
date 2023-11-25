package main.controllers;

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
import main.model.Alteracao;
import main.model.Tarefa;
import main.services.AlteracoesServices;
import main.services.TarefaServices;
import main.views.gridview.AuditoriaGridView;
import main.views.gridview.TarefasGridView;

public class AuditoriasController implements Inicializador , Resumidor{  

    @FXML
    private GridPane auditoriasGrid;
    
    @FXML
    private StackPane stackPaneScroll;

    @FXML
    private TextField textFieldBuscarAuditoria;

    private AlteracoesServices alteracoesServices;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) { 
        initialize();
        initializeViews(contentFather,primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        alteracoesServices = new AlteracoesServices();
    }
    
    public void initializeViews(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        List<Alteracao> alteracoes = alteracoesServices.ObterAlteracoes();
        criarGridComResultados(alteracoes);
    }
  
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){

        textFieldBuscarAuditoria.setOnKeyPressed(e ->{
            String alteracoes = textFieldBuscarAuditoria.getText();
            if(e.getCode().equals(ENTER)){
                  List<Alteracao> alteracoesBuscadas = alteracoesServices.EncontrarAlteracoesPorDescricao(alteracoes);
                  criarGridComResultados(alteracoesBuscadas);
            }
        });        
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        initializeViews(contentFather,primmaryStage, blackShadow);
    }

    private void criarGridComResultados(List<Alteracao> alteracoes) {
        AuditoriaGridView auditoriaGridView = new AuditoriaGridView(auditoriasGrid, 1, alteracoes, stackPaneScroll);
        auditoriaGridView.createGridAsync();  
    }
}
