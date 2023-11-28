/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Acao;
import main.interfaces.InicializadorComAcao;
import main.interfaces.InicializadorComOrigemEDado;

public class RemoverController extends CustomController implements InicializadorComAcao{
    
     @FXML
    private Label mensagem;

    @FXML
    private Button confirmar;
    
    @FXML
    private Button cancelar;


    @Override
    public void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao, Object[] dados) {
        String msg = ObterDadoArray(dados, 0).toString();
        mensagem.setText(msg);
        
        confirmar.setOnMouseClicked(e->{
           acao.RealizarAcao(new Object[]{});
           App.getInstance().FecharDialog(primmaryStage, blackShadow);                  
        });  
        
        cancelar.setOnMouseClicked(e->{
           App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });    
    }
    

}

