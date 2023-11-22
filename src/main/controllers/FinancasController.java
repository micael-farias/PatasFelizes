package main.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Despesa;
import main.model.Doacao;
import main.services.DoacaoServices;
import main.services.DespesaServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_DESPESA;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;
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
    private StackPane stackPaneScroll;
    
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
        criarDespesas(contentFather, primaryStage, blackShadow);
    }
    
    public void initalizeViews(Pane contentFather, Stage primaryStage, Pane blackShadow){
        toggleViewFinancas = new ToggleView();
        toggleViewFinancas.CriarToggle(toogleFinancas,
                e -> {
                    criarDespesas(contentFather, primaryStage, blackShadow);
                    novaDespesaButton.setText("Nova despesa");
                },
                e -> {                              
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
    }
    
    public void criarDoacoes(Pane contentFather, Stage primaryStage, Pane blackShadow){
        List<Doacao> doacoes = doacaoServices.ObterDoacoes();
        DoacoesGridView doacoesGridView = new DoacoesGridView(despesasGrid, 1, doacoes, contentFather, stackPaneScroll, primaryStage, blackShadow);
        doacoesGridView.createGridAsync();
    }

    private void criarDespesas(Pane contentFather, Stage primaryStage, Pane blackShadow) {
        List<Despesa> despesas = despesaServices.ObterDespesas();
        DespesasGridView despesasGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather, stackPaneScroll, primaryStage, blackShadow);
        despesasGridView.createGridAsync();    
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        if(toggleViewFinancas.getSelectedItem() == ToogleEnum.DIREITO){
            criarDespesas(contentFather, primmaryStage, blackShadow);
        }else{
            criarDoacoes(contentFather, primmaryStage, blackShadow);
        }
    }
}
