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
        initialize(contentFather);
        initalizeViews(contentFather);
        setListeners(contentFather, primmaryStage, blackShadow);      
    }
    public void initialize(Pane contentFather){
        despesaServices = new DespesaServices();
        doacaoServices = new DoacaoServices();
        criarDespesas(contentFather);
    }
    
    public void initalizeViews(Pane contentFather){
        toggleViewFinancas = new ToggleView();
        toggleViewFinancas.CriarToggle(toogleFinancas,
                e -> criarDespesas(contentFather),
                e -> criarDoacoes(contentFather));
        
        toggleViewFinancas.setTextoDireito("Despesas");
        toggleViewFinancas.setTextoEsquerdo("Doações");
    
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        novaDespesaButton.setOnMouseClicked(e ->{
            String dialog = (toggleViewFinancas.getSelectedItem() == ToogleEnum.DIREITO ) ? DIALOG_CADASTRAR_DESPESA : DIALOG_CADASTRAR_DOACAO;
            App.getInstance().AbrirDialogComDado(dialog, contentFather, primmaryStage, blackShadow, null);
        });
    }
    
    public void criarDoacoes(Pane contentFather){
        List<Doacao> doacoes = doacaoServices.ObterDoacoes();
        DoacoesGridView doacoesGridView = new DoacoesGridView(despesasGrid, 1, doacoes, contentFather, stackPaneScroll);
        doacoesGridView.createGridAsync();
    }

    private void criarDespesas(Pane contentFather) {
        List<Despesa> despesas = despesaServices.ObterDespesas();
        DespesasGridView despesasGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather, stackPaneScroll);
        despesasGridView.createGridAsync();    
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
    }
}
