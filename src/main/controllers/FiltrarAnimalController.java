package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import static main.utils.Constantes.FORM_HOME;

public class FiltrarAnimalController implements Inicializador{

    @FXML
    private Button salvarFiltro;
    
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        salvarFiltro.setOnMouseClicked(e->{
                App.getInstance().EntrarTela(FORM_HOME, contentFather, primmaryStage, blackShadow);
                    
        });
    }
}
