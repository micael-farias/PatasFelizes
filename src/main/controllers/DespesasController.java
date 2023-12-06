package main.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.enums.Mapping;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Despesa;
import static main.model.Despesa.somarValores;
import main.model.FiltroDespesa;
import main.services.DespesaServices;
import static main.utils.Constantes.DIALOG_FILTRAR_DESPESAS;
import static main.utils.Constantes.FORM_DESPESAS;
import static main.utils.DateHelper.invalidString;
import main.utils.RealFormatter;
import main.views.gridview.DespesasGridView;
import main.views.gridview.FiltroGridView;
import main.views.toggle.FiltroView;

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
    private Pane filtros;

    @FXML
    private Label totalDespesas;
    private DespesaServices despesaServices;
    private FiltroView filtroView;
    private FiltroGridView filtroGridView;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize(contentFather, primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);      
    }
    public void initialize(Pane contentFather, Stage primaryStage, Pane blackShadow){
        despesaServices = new DespesaServices();
        filtroView = new FiltroView();
        filtroView.Criar(filtros);
        criarDespesas(contentFather, primaryStage, blackShadow);
    }
    
     public void criarFiltros(){
        if(DespesaServices.filtro == null){
            filtros.setVisible(false);
            return;
        }
        filtros.setVisible(true);
        var key = Mapping.GetKeyByValue(Mapping.getOrdenacoesDespesaDoacoesHash(), DespesaServices.filtro.getOrdenacao());
        if(invalidString(key)){
            filtroView.removerOrdenacao();
        }else{
            filtroView.adicionarCaixaOrdenados();
            filtroView.setOrdenacao(key);
        }
        
        
        var filtrosString = new ArrayList<>(DespesaServices.filtro.GetFiltros().values());  
        if(!filtrosString.isEmpty()){     
            filtroView.adicionarCaixaFiltrados();
            filtroGridView = new FiltroGridView(filtroView.getGridFiltros(), filtrosString.size(), filtrosString);
            filtroGridView.createGridAsync();
        }else{
            filtroView.removerFiltros();
        }
        
        if(invalidString(key) && filtrosString.isEmpty()) filtros.setVisible(false);
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
                DespesaServices.filtro = (FiltroDespesa) dados[1];
                criarGridDespesaComResultados(despesas,contentFather, primmaryStage, blackShadow);
            });
            
        });
        

        
        filtroView.excluirFiltro((dado) ->{
            DespesaServices.filtro = null;
            criarDespesas(contentFather, primmaryStage, blackShadow);
            criarFiltros();
        
        });  
        
    }
    
    public void calcularTotal(List<Despesa> despesas){
        double valorTotal = somarValores(despesas);
        totalDespesas.setText(RealFormatter.formatarComoReal(valorTotal));
    }

    private void criarDespesas(Pane contentFather, Stage primaryStage, Pane blackShadow){
        List<Despesa> despesas;
        if(DespesaServices.filtro != null){
            despesas = despesaServices.FiltrarDespesas(DespesaServices.filtro);    
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
        criarFiltros();
    }
 
}
