package main.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Despesa;
import main.model.FiltroDespesa;
import main.model.Procedimento;
import main.services.DoacaoServices;
import main.services.DespesaServices;
import static main.utils.Constantes.DIALOG_FILTRAR_DESPESAS;
import static main.utils.Constantes.FORM_DESPESAS;
import main.utils.RealFormatter;
import main.views.gridview.DespesasGridView;
import main.views.toggle.ToggleView;

public class DespesasController implements Inicializador, Resumidor {  
    
    @FXML
    private GridPane despesasGrid;
    @FXML
    private StackPane stackPaneScroll;
       
    @FXML
    private TextField textFieldBuscarFinança;
    
    @FXML
    private Button filtrarDespesas;

    @FXML
    private Label totalDespesas;
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
        
        filtrarDespesas.setOnMouseClicked(e -> {
            App.getInstance().AbrirDialogComAcao(DIALOG_FILTRAR_DESPESAS, FORM_DESPESAS, contentFather, primmaryStage, blackShadow, null, (dados) ->{
                List<Despesa> despesas = (List<Despesa>)dados[0];
                despesaServices.filtro = (FiltroDespesa) dados[1];
                criarGridDespesaComResultados(despesas,contentFather, primmaryStage, blackShadow);
            });
            
        });
        
    }
    
    public void calcularTotal(List<Despesa> despesas){
        double valorTotal = Despesa.somarValores(despesas);
        totalDespesas.setText(RealFormatter.formatarComoReal(valorTotal));;
    }

    private void criarDespesas(Pane contentFather, Stage primaryStage, Pane blackShadow){
        List<Despesa> despesas;
        if(despesaServices.filtro != null){
            despesas = despesaServices.FiltrarDespesas(despesaServices.filtro);    
            criarGridDespesaComResultados(despesas, contentFather, primaryStage, blackShadow);   
        }else{
            despesas = despesaServices.ObterDespesas();
            criarGridDespesaComResultados(despesas, contentFather, primaryStage, blackShadow);     
        }
    }
       
    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        criarDespesas(contentFather, primmaryStage, blackShadow);
        
    }


    private void criarGridDespesaComResultados(List<Despesa> despesas, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        DespesasGridView despesasGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather, stackPaneScroll, primmaryStage, blackShadow);
        despesasGridView.createGridAsync();   
        calcularTotal(despesas);
    }
 
}
