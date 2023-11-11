package main.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.interfaces.Inicializador;
import main.model.Animal;
import main.views.gridview.DespesasGridView;

public class FinancasController implements Inicializador {  

  
    @FXML
    private GridPane despesasGrid;
    
    

    public static List<Animal> getListaDeAnimais() {
        List<Animal> animais = new ArrayList<>();

    
        return FXCollections.observableArrayList(animais);
    } 

    @Override
    public void Inicializar(Pane contentFather, Pane blackShadow) {
        List<Animal> animais = getListaDeAnimais();
        DespesasGridView animalGridView = new DespesasGridView(despesasGrid, 1, animais, contentFather);
        animalGridView.createGrid();
    }
  
}
