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
import main.interfaces.InicializadorComOrigemEDado;

public class RemoverController extends CustomController implements InicializadorComOrigemEDado{
    
     @FXML
    private Label mensagem;

    @FXML
    private Button confirmar;
    
    @FXML
    private Button cancelar;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, String origem, Object[] dados) {
       String msg = ObterDadoArray(dados, 0).toString();
       mensagem.setText(msg);
        
        confirmar.setOnMouseClicked(e->{
           App.getInstance().EntrarTelaOnResume(origem, contentFather, primmaryStage, blackShadow, null);                  
        });  
        
        cancelar.setOnMouseClicked(e->{
            App.getInstance().EntrarTelaNoAction(origem, contentFather, primmaryStage, blackShadow);
        });
    }
    

}

