/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.Animal;
import main.utils.ImageLoader;
import main.utils.Rectangles;
import main.views.toggle.ToggleView;

/**
 *
 * @author micha
 */
public class AnimalFormularioController extends CustomController{
    
    public ToggleView configuraToggleSexo(Pane toggleSexo) {
        ToggleView toggleViewSexo = new ToggleView();
        toggleViewSexo.CriarToggle(toggleSexo, null, null);
        toggleViewSexo.setImagemDireita("marte-cinza.png");
        toggleViewSexo.setImagemEsquerda("venus-cinza.png");
        return toggleViewSexo;
    }

    public ToggleView configuraToggleCastrado(Pane toogleCastrado) {
        ToggleView toogleViewCastrado = new ToggleView();
        toogleViewCastrado.CriarToggle(toogleCastrado, null, null);
        toogleViewCastrado.setTextoDireito("SIM");
        toogleViewCastrado.setTextoEsquerdo("NAO");
        return toogleViewCastrado;
    }

    public static byte[] CarregarImagem(Stage primaryStage, ImageView image, VBox layoutImageView) {
        byte[] foto = ImageLoader.CarregarImagemLocal(primaryStage);
        if (foto != null) {
            ImageLoader.CarregarImagem(image, foto, "", Rectangles.GetRectangleImageAnimais());
            image.setFitHeight(layoutImageView.heightProperty().doubleValue());
            image.setFitWidth(layoutImageView.widthProperty().doubleValue());
        }
        
        return foto;
    }
    
      public static byte[] CarregarImagemv2(Stage primaryStage, ImageView image, VBox layoutImageView, byte[] foto) {
        if (foto != null) {
            ImageLoader.CarregarImagem(image, foto, "", Rectangles.GetRectangleImageAnimais());
            image.setFitHeight(layoutImageView.heightProperty().doubleValue());
            image.setFitWidth(layoutImageView.widthProperty().doubleValue());
        }
        
        return foto;
    }
    
    
    public TextFormatter criarTextFormatter(int tamanho){
        return new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > tamanho) {
                    return null; // Ignora a mudança se exceder o comprimento máximo
                }
            }
            return change;
        });
    }
    
    public void manterTexto(TextField anosAnimalTextField){
        String currentText = anosAnimalTextField.getText();
        String newText = currentText.replaceAll("[^\\d]", "");
        anosAnimalTextField.setText(newText);
        anosAnimalTextField.positionCaret(newText.length());   
    }
}
