/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorDialog;
import main.views.toggle.ToggleView;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class CadastrarAnimalController implements InicializadorDialog {


    @FXML
    private Button salvarAnimal;

    @FXML
    private HBox toggleCastrado;

    @FXML
    private HBox toggleSexo;

    @FXML
    private HBox toogleVermifugado;

 
    ToggleView toggleViewSexo;
    ToggleView toogleViewCastrado;
    ToggleView toogleViewVermifugado;
    
    private Stage stage;
    
    @Override
    public void Inicializar(Pane contentFather, Pane blackShadow) {
        
        toggleViewSexo = new ToggleView();
        toogleViewCastrado = new ToggleView();
        toogleViewVermifugado = new ToggleView();
        
        toggleViewSexo.CriarToogle(toggleSexo);
        toogleViewCastrado.CriarToogle(toggleCastrado);
        toogleViewVermifugado.CriarToogle(toogleVermifugado);
        
        toggleViewSexo.setImagemDireita("marte-cinza.png");
        toggleViewSexo.setImagemEsquerda("venus-cinza.png");
        
        toogleViewCastrado.setTextoDireito("SIM");
        toogleViewCastrado.setTextoEsquerdo("NAO");
          
        toogleViewVermifugado.setTextoDireito("SIM");
        toogleViewVermifugado.setTextoEsquerdo("NAO");
        
        salvarAnimal.setOnMouseClicked(e->{
            if(stage !=null){
                stage.close();
                App.getInstance().EntrarTelaInicial(contentFather, blackShadow);
           }             
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }    

    
}
