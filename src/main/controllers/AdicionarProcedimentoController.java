package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.App;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;

public class AdicionarProcedimentoController {

    @FXML
    private Label labelAdicionarProcedimento;

    @FXML
    private ImageView iconClick;


            
    public void setOnClick(Pane contentFather, Stage primmaryStage, Pane blackShadow, int idAnimal) {
      
      iconClick.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_PROCEDIMENTO, contentFather, primmaryStage, blackShadow, new Object[]{idAnimal});   
      });    

      labelAdicionarProcedimento.setOnMouseClicked(e -> {         
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_PROCEDIMENTO, contentFather, primmaryStage, blackShadow, new Object[]{idAnimal});
      });

    }
}