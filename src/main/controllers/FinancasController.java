package main.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Animal;
import main.model.Despesa;
import main.model.Doacao;
import main.services.DoacaoServices;
import main.services.DespesaServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_DESPESA;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;
import main.utils.RealFormatter;
import main.utils.ToogleEnum;
import main.views.gridview.DespesasGridView;
import main.views.gridview.DoacoesGridView;
import main.views.toggle.ToggleView;

public class FinancasController implements Inicializador, Resumidor {  
    
    @FXML
    private HBox toogleFinancas;
  
    @FXML
    private GridPane despesasGrid;
    
    @FXML
    private Button novaDespesaButton;
    
    @FXML
    private Label labelTotalDespesa;

    @FXML
    private Label labelTotalReceita;
    
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
        initalizeViews(contentFather, primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);      
    }
    public void initialize(Pane contentFather, Stage primaryStage, Pane blackShadow){
        despesaServices = new DespesaServices();
        doacaoServices = new DoacaoServices();
        criarDoacoes(contentFather, primaryStage, blackShadow);
        setarValores();
        setHintDoacoes();
    }
    
    public void setHintDespesa(){
        textFieldBuscarFinança.setPromptText("Buscar despesa por descrição");
    }
    
    public void setHintDoacoes(){
        textFieldBuscarFinança.setPromptText("Buscar doação por doador"); 
    }
    
    public void initalizeViews(Pane contentFather, Stage primaryStage, Pane blackShadow){
        toggleViewFinancas = new ToggleView();
        toggleViewFinancas.CriarToggle(toogleFinancas,
                e -> {
                    setHintDespesa();
                    criarDespesas(contentFather, primaryStage, blackShadow);
                    novaDespesaButton.setText("Nova despesa");
                },
                e -> {    
                    setHintDoacoes();
                    criarDoacoes(contentFather, primaryStage, blackShadow);
                    novaDespesaButton.setText("Nova doação");
                });
        
        toggleViewFinancas.setTextoDireito("Despesas");
        toggleViewFinancas.setTextoEsquerdo("Doações");
    
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        novaDespesaButton.setOnMouseClicked(e ->{
            String dialog = (toggleViewFinancas.getSelectedItem() == ToogleEnum.DIREITO ) ? DIALOG_CADASTRAR_DESPESA : DIALOG_CADASTRAR_DOACAO;
            App.getInstance().AbrirDialogComDado(dialog, contentFather, primmaryStage, blackShadow, null);
        });
        
        textFieldBuscarFinança.setOnKeyPressed(e ->{
            boolean ehDespesa = toggleViewFinancas.getSelectedItem() == ToogleEnum.DIREITO;     
            String texto = textFieldBuscarFinança.getText();
            
            if(e.getCode().equals(ENTER)){
                if(ehDespesa){
                    List<Despesa> despesas = despesaServices.ObterDespesasPorDescricao(texto);
                    criarGridDespesaComResultados(despesas, contentFather, primmaryStage, blackShadow);              
                }else{
                    List<Doacao> doacoes = doacaoServices.ObterDoacoesPorDoador(texto);
                    criarGridReceitaComResultados(doacoes, contentFather, primmaryStage, blackShadow);
                }                  
            }else if(e.getCode().equals(BACK_SPACE)){               
                if(texto.length() == 0 && ehDespesa){
                    setHintDespesa();
                }else if(texto.length() == 0){
                    setHintDoacoes();
                }
            }
        });
        
    }
    
    public void criarDoacoes(Pane contentFather, Stage primaryStage, Pane blackShadow){
        List<Doacao> doacoes = doacaoServices.ObterDoacoes();
        criarGridReceitaComResultados(doacoes, contentFather, primaryStage, blackShadow);
    }

    private void criarDespesas(Pane contentFather, Stage primaryStage, Pane blackShadow){
        List<Despesa> despesas = despesaServices.ObterDespesas();
        criarGridDespesaComResultados(despesas, contentFather, primaryStage, blackShadow);       
    }
    
    

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        if(toggleViewFinancas.getSelectedItem() == ToogleEnum.DIREITO){
            criarDespesas(contentFather, primmaryStage, blackShadow);
        }else{
            criarDoacoes(contentFather, primmaryStage, blackShadow);
        }
    }

    
    private void setarValores(){
        double[] valores = doacaoServices.ObterTotalReceitaEDespesa();

        labelTotalReceita.setText(RealFormatter.formatarComoReal(valores[1]));
        labelTotalDespesa.setText(RealFormatter.formatarComoReal(valores[0]));    
   }

    private void criarGridDespesaComResultados(List<Despesa> despesas, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        DespesasGridView despesasGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather, stackPaneScroll, primmaryStage, blackShadow);
        despesasGridView.createGridAsync();   
        setarValores();
    }
        
    private void criarGridReceitaComResultados(List<Doacao> doacoes, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        DoacoesGridView doacoesGridView = new DoacoesGridView(despesasGrid, 1, doacoes, contentFather, stackPaneScroll, primmaryStage, blackShadow);
        doacoesGridView.createGridAsync();   
        setarValores();
    }
}
