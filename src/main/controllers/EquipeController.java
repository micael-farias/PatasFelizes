package main.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.model.Voluntario;
import static main.utils.Constantes.PATH_IMAGES;
import main.views.gridview.EquipeGridView;

public class EquipeController implements Inicializador {

    @FXML
    private GridPane membrosGrid;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
          List<Voluntario> voluntarios = new ArrayList<>();
        voluntarios.add(new Voluntario("Alexandre Toledo", PATH_IMAGES +"alexandre.jpeg", "alexandre@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Dinah Toledo", PATH_IMAGES +"dina.jpeg", "dinah@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Graziela Rodrigues", PATH_IMAGES+"grazi.jpg", "grazi@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Michael Farias", PATH_IMAGES+"michael.png", "michael@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Pedro Emanuel", PATH_IMAGES+"pedro.png", "pedro@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Wania Kelly", PATH_IMAGES+"wania.png", "wania@patasfelizes.com.br","85997654398"));
         voluntarios.add(new Voluntario("Alexandre Toledo", PATH_IMAGES +"alexandre.jpeg", "alexandre@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Dinah Toledo", PATH_IMAGES +"dina.jpeg", "dinah@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Graziela Rodrigues", PATH_IMAGES+"grazi.jpg", "grazi@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Michael Farias", PATH_IMAGES+"michael.png", "michael@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Pedro Emanuel", PATH_IMAGES+"pedro.png", "pedro@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Wania Kelly", PATH_IMAGES+"wania.png", "wania@patasfelizes.com.br","85997654398"));
         voluntarios.add(new Voluntario("Alexandre Toledo", PATH_IMAGES +"alexandre.jpeg", "alexandre@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Dinah Toledo", PATH_IMAGES +"dina.jpeg", "dinah@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Graziela Rodrigues", PATH_IMAGES+"grazi.jpg", "grazi@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Michael Farias", PATH_IMAGES+"michael.png", "michael@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Pedro Emanuel", PATH_IMAGES+"pedro.png", "pedro@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Wania Kelly", PATH_IMAGES+"wania.png", "wania@patasfelizes.com.br","85997654398"));
        
        EquipeGridView animalGridView = new EquipeGridView(membrosGrid, 1, voluntarios, contentFather);
        animalGridView.createGrid();
    }
}
