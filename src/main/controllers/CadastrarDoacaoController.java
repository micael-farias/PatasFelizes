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
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.FORM_FINANCAS;

/**
 *
 * @author micha
 */
public class CadastrarDoacaoController implements Inicializador{
    @FXML
    private TextField dataDoacao;

    @FXML
    private TextField descricaoDoacao;

    @FXML
    private Button salvarDoacao;

    @FXML
    private TextField tipoDoacao;

    @FXML
    private TextField valorDoacao;

    @FXML
    private TextField voluntárioDoacao;
    
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
       salvarDoacao.setOnMouseClicked(e->{
 
                App.getInstance().EntrarTela(FORM_FINANCAS ,contentFather, primmaryStage, blackShadow);
                        
        });
    }
    
}
