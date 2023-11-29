package main.controllers;

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
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Despesa;
import main.model.Doacao;
import main.model.FiltroDespesa;
import main.services.DoacaoServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;
import static main.utils.Constantes.DIALOG_FILTRAR_DOACOES;
import static main.utils.Constantes.FORM_DOACOES;
import main.utils.RealFormatter;
import main.views.gridview.DoacoesGridView;

public class DoacoesController implements Inicializador, Resumidor {  
    
    @FXML
    private GridPane doacoesGrid; 
    
    @FXML
    private StackPane stackPaneScroll;
       
    @FXML
    private TextField textFieldBuscarDoacoes;
    
    @FXML
    private Button filtrarDoacoes;
    
    @FXML
    private Label totalDoacoes;
    
    private DoacaoServices doacaoServices;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize(contentFather, primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);      
    }
    public void initialize(Pane contentFather, Stage primaryStage, Pane blackShadow){
        doacaoServices = new DoacaoServices();
        criarDoacoes(contentFather, primaryStage, blackShadow);
    }  
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        textFieldBuscarDoacoes.setOnKeyPressed(e ->{
            String texto = textFieldBuscarDoacoes.getText();         
            if(e.getCode().equals(ENTER)){
                List<Doacao> doacoes = doacaoServices.ObterDoacoesPorDoador(texto);
                criarGridDoacoesComResultados(doacoes, contentFather, primmaryStage, blackShadow);                            
            }
        });
        
        filtrarDoacoes.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComAcao(DIALOG_FILTRAR_DOACOES, FORM_DOACOES, contentFather, primmaryStage, blackShadow, null, (dados) ->{
                List<Doacao> doacoes = (List<Doacao>)dados[0];
                doacaoServices.filtro = (FiltroDespesa) dados[1];
                criarGridDoacoesComResultados(doacoes,contentFather, primmaryStage, blackShadow);
            
            });
        });
    }
    
    public void criarDoacoes(Pane contentFather, Stage primaryStage, Pane blackShadow){
        if(doacaoServices.filtro != null){
           List<Doacao> doacoesFiltradas = doacaoServices.FiltrarDoacoes(doacaoServices.filtro);
            criarGridDoacoesComResultados(doacoesFiltradas, contentFather, primaryStage, blackShadow);
        }else{
           List<Doacao> doacoes = doacaoServices.ObterDoacoes();
           criarGridDoacoesComResultados(doacoes, contentFather, primaryStage, blackShadow);
        }
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        criarDoacoes(contentFather, primmaryStage, blackShadow);
    }
        
    private void criarGridDoacoesComResultados(List<Doacao> doacoes, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        DoacoesGridView doacoesGridView = new DoacoesGridView(doacoesGrid, 1, doacoes, contentFather, stackPaneScroll, primmaryStage, blackShadow);
        doacoesGridView.createGridAsync();   
        calcularTotal(doacoes);
    }
    
    public void calcularTotal(List<Doacao> doacoes){
        double valorTotal = Doacao.somarValores(doacoes);
        totalDoacoes.setText(RealFormatter.formatarComoReal(valorTotal));;
    }
}
