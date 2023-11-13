package main.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.model.Despesa;
import main.model.Doacao;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import main.views.gridview.AnimalGridView;

import main.views.gridview.DespesasGridView;
import main.views.gridview.DoacoesGridView;
import main.views.gridview.GridView;
import main.views.toggle.ToggleView;

public class FinancasController implements Inicializador {  
    
    @FXML
    private HBox toogleFinancas;
  
    @FXML
    private GridPane despesasGrid;
    
    @FXML
    private Button novaDespesaButton;
    
    @FXML
    private StackPane stackPaneScroll;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        List<Despesa> despesas = new ArrayList<>();
        despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 3", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
         despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 3", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
         despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 3", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
         despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 3", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 3", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 1", 100.0, new Date()));
        despesas.add(new Despesa("Despesa 2", 100.0, new Date()));
        
        List<Doacao> doacoes = new ArrayList<>();
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        
        EventHandler<WorkerStateEvent> onFinish = event -> {
            // O código a ser executado após a criação da GridPane
            System.out.println("GridPane criada com sucesso!");
        };
        ToggleView toggleViewFinancas = new ToggleView();
        toggleViewFinancas.CriarToggle(toogleFinancas,
            e -> {
                System.out.println("Chamado 1");
                DespesasGridView despesasGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather, stackPaneScroll);
                despesasGridView.createGridAsync();
           },
            e -> {
                System.out.println("Chamado 2");
                DoacoesGridView doacoesGridView = new DoacoesGridView(despesasGrid, 1, doacoes, contentFather, stackPaneScroll);
                doacoesGridView.createGridAsync();
            }
        );
        
        toggleViewFinancas.setTextoDireito("Despesas");
        toggleViewFinancas.setTextoEsquerdo("Doações");
  
        DespesasGridView animalGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather, stackPaneScroll);
        animalGridView.createGridAsync();
        
        novaDespesaButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_DOACAO, contentFather, primmaryStage, blackShadow);
        });
    
}



 
}
