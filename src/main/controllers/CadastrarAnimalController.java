/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import main.views.toggle.ToggleView;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class CadastrarAnimalController implements Initializable {

    @FXML
    private HBox toggleSexo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleView tglSexo = new ToggleView();
        tglSexo.CriarToogle(toggleSexo);
        tglSexo.onClickImagemDireita(e ->{
            System.out.println("    Masculino   ");
        });
    }    
    
}
