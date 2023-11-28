package main.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Despesa;
import main.services.DoacaoServices;
import main.services.DespesaServices;
import main.views.gridview.DespesasGridView;
import main.views.toggle.ToggleView;

public class DespesasController implements Inicializador, Resumidor {  
    
    @FXML
    private GridPane despesasGrid;
    @FXML
    private StackPane stackPaneScroll;
       
    @FXML
    private TextField textFieldBuscarFinança;
    
    private DespesaServices despesaServices;
    private DoacaoServices doacaoServices;
    private ToggleView toggleViewFinancas;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize(contentFather, primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);      
    }
    public void initialize(Pane contentFather, Stage primaryStage, Pane blackShadow){
        despesaServices = new DespesaServices();
        doacaoServices = new DoacaoServices();
        setarValores();
        criarDespesas(contentFather, primaryStage, blackShadow);
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){

        textFieldBuscarFinança.setOnKeyPressed(e ->{
            String texto = textFieldBuscarFinança.getText();
            
            if(e.getCode().equals(ENTER)){
                List<Despesa> despesas = despesaServices.ObterDespesasPorDescricao(texto);
                criarGridDespesaComResultados(despesas, contentFather, primmaryStage, blackShadow);              
            }
        });
        
    }

    private void criarDespesas(Pane contentFather, Stage primaryStage, Pane blackShadow){
        List<Despesa> despesas = despesaServices.ObterDespesas();
        criarGridDespesaComResultados(despesas, contentFather, primaryStage, blackShadow);       
    }
       
    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        criarDespesas(contentFather, primmaryStage, blackShadow);
    }

    
    private void setarValores(){
        double[] valores = doacaoServices.ObterTotalReceitaEDespesa();
   }

    private void criarGridDespesaComResultados(List<Despesa> despesas, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        DespesasGridView despesasGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather, stackPaneScroll, primmaryStage, blackShadow);
        despesasGridView.createGridAsync();   
        setarValores();
    }
}
