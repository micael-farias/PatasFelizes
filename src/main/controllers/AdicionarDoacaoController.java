package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.App;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;

public class AdicionarDoacaoController {

    @FXML
    private Label labelAdicionarDoacao;

    @FXML
    private ImageView iconClick;


            
    public void setOnClick(Pane contentFather, Stage primmaryStage, Pane blackShadow, String mensagem, String tela) {
      labelAdicionarDoacao.setText(mensagem);
      
      iconClick.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComDado(tela, contentFather, primmaryStage, blackShadow, null);   
      });    

      labelAdicionarDoacao.setOnMouseClicked(e -> {         
            App.getInstance().AbrirDialogComDado(tela, contentFather, primmaryStage, blackShadow, null);
      });

    }
}