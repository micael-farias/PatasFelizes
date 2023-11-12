/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorDialog;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;

/**
 *
 * @author micha
 */
public class CadastrarProcedimentoController implements InicializadorDialog{
    @FXML
    private TextField dataProcedimento;

    @FXML
    private TextField descricaoProcedimento;

    @FXML
    private Button salvarProcedimento;

    @FXML
    private TextField tipoProcedimento;

    @FXML
    private TextField valorProcedimento;

    @FXML
    private TextField voluntárioProcedimento;
    
    private Stage stage;
    
    @Override
    public void Inicializar(Pane contentFather, Pane blackShadow) {
       salvarProcedimento.setOnMouseClicked(e->{
            if(stage !=null){
                stage.close();
                App.getInstance().EntrarTela(FORM_ANIMAL_DETALHES ,contentFather, null, blackShadow);
           }             
        });
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
