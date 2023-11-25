package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.model.Alteracao;
import main.services.AlteracoesServices;



public class AuditoriaController{

    @FXML
    private VBox layoutAlteracao;
    
    @FXML
    private Label identificador;

    @FXML
    private Label mensagem;

    private AlteracoesServices alteracoesServices;

    public void setData(Alteracao alteracao, int posicao) {
        mensagem.setText(alteracao.getMensagem());
        identificador.setText(alteracao.getIdentificador());
        
         if(posicao % 2 == 0){
            layoutAlteracao.setStyle("-fx-background-color: white;");
        }    
        
    }
}