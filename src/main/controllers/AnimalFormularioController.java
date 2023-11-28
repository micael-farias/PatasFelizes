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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.model.Animal;
import static main.utils.Constantes.TOOGLE_BUTTON_SHORT;
import main.utils.ImageLoader;
import main.utils.Rectangles;
import main.views.toggle.ToggleView;

/**
 *
 * @author micha
 */
public class AnimalFormularioController extends CustomController{
    
    public ToggleView configuraToggleSexo(Pane toggleSexo) {
        ToggleView toggleViewSexo = new ToggleView(TOOGLE_BUTTON_SHORT);
        toggleViewSexo.CriarToggle(toggleSexo, null, null);
        toggleViewSexo.setImagemDireita("marte-cinza.png");
        toggleViewSexo.setImagemEsquerda("venus-cinza.png");
        return toggleViewSexo;
    }

    public ToggleView configuraToggleCastrado(Pane toogleCastrado) {
        ToggleView toogleViewCastrado = new ToggleView(TOOGLE_BUTTON_SHORT);
        toogleViewCastrado.CriarToggle(toogleCastrado, null, null);
        toogleViewCastrado.setTextoDireito("SIM");
        toogleViewCastrado.setTextoEsquerdo("NÃO");
        return toogleViewCastrado;
    }
    
        
    public static void textFormatter(TextField mesesTextField, TextField anoTextField) {
       // Criar um TextFormatter que aceita apenas números de 1 a 12
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Verificar se o novo texto é um número de 1 a 12
            if (newText.matches("([1-9]|1[0-2])?")) {
                return change;  // Aceitar a alteração
            } else {
                return null;  // Rejeitar a alteração
            }
        });

        // Adicionar o TextFormatter ao TextField
        mesesTextField.setTextFormatter(textFormatter);
        
           // Criar um TextFormatter que aceita apenas números de 1 a 30
        TextFormatter<String> textFormatter2 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            // Verificar se o novo texto é um número de 1 a 30
            if (newText.matches("([1-9]|[12][0-9]|30)?")) {
                return change;  // Aceitar a alteração
            } else {
                return null;  // Rejeitar a alteração
            }
        });

        // Adicionar o TextFormatter ao TextField
        anoTextField.setTextFormatter(textFormatter2);
    }

    public static byte[] CarregarImagem(Stage primaryStage, ImageView image, VBox layoutImageView, Rectangle clip) {
        byte[] foto = ImageLoader.CarregarImagemLocal(primaryStage);
        if (foto != null) {
            ImageLoader.CarregarImagem(image, foto, "", clip);
            System.out.println(layoutImageView.heightProperty().doubleValue());
            System.out.println(layoutImageView.widthProperty().doubleValue());
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
