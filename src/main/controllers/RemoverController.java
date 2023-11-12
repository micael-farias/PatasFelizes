/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComOrigem;
import main.model.Despesa;
import main.model.Tarefa;
import static main.utils.Constantes.FORM_HOME;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateToString.DataParaString;
import main.utils.ImageLoader;
import static main.utils.ImageLoader.CarregarImagem;

public class RemoverController implements InicializadorComOrigem{
    
     @FXML
    private Label mensagem;

    @FXML
    private Button confirmar;
    

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, String origem) {
        confirmar.setOnMouseClicked(e->{
                App.getInstance().EntrarTela(origem, contentFather, primmaryStage, null, blackShadow);
                    
        });

    }
}

