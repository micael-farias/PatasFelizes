package main.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.interfaces.Inicializador;
import main.model.Despesa;

import main.views.gridview.DespesasGridView;

public class FinancasController implements Inicializador {  

  
    @FXML
    private GridPane despesasGrid;
    
    

    @Override
    public void Inicializar(Pane contentFather, Pane blackShadow) {
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
        
        
        DespesasGridView animalGridView = new DespesasGridView(despesasGrid, 1, despesas, contentFather);
        animalGridView.createGrid();
    }
  
}
