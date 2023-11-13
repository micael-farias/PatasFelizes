package main.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import main.utils.ToogleEnum;
import main.utils.ToogleLado;

public class ToogleButtonController {

    @FXML
    private StackPane direito;

    @FXML
    private StackPane esquerdo;
    
    @FXML
    private ImageView imagemDireita;

    @FXML
    private ImageView imagemEsquerda;
    
    @FXML
    private Label labelDireita;

    @FXML
    private Label labelEsquerda;
    
    public ToogleLado lado;

    String shadow = "-fx-effect: dropshadow(three-pass-box, rgba(22, 22, 22, 0.4), 0, 1, 2, 0);";
    
    public void setListeneres(EventHandler<MouseEvent> clickDireita, EventHandler<MouseEvent> clickEsquerda){
      
        direito.setOnMousePressed(e -> {
            
            lado = ToogleLado.DIREITO;
            direito.setStyle("-fx-background-color: #A8C4F8;" + shadow);
            esquerdo.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
            if (clickDireita != null) {
                MouseEvent event = new MouseEvent(direito, direito, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, false, false, false, null);
                clickDireita.handle(event);
            } 
        });

        esquerdo.setOnMousePressed(e -> {
            lado = ToogleLado.ESQUEDO;
            esquerdo.setStyle("-fx-background-color: #A8C4F8;"  + shadow);
            direito.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
            if (clickDireita != null) {
                MouseEvent event = new MouseEvent(esquerdo, esquerdo, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, false, false, false, null);
                clickEsquerda.handle(event);
            } 
        });
        
    }
    
    public void ativarBotao(ToogleEnum opcao){
        switch (opcao) {
            case DIREITO -> {
                direito.setStyle("-fx-background-color: #A8C4F8;"  + shadow);
                esquerdo.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
            }
            case ESQUERDO -> {
                esquerdo.setStyle("-fx-background-color: #A8C4F8; "  + shadow);
                direito.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
            }
            default -> {
            }
        }
    }
    
    public void setImagemDireita(String imagem){
        imagemDireita.setImage(new Image("/assets/images/" + imagem));
        labelDireita.setVisible(false);
        imagemDireita.setVisible(true);
    }
    
    public void setImagemEsquerda(String imagem){
        imagemEsquerda.setImage(new Image("/assets/images/" + imagem));
        labelEsquerda.setVisible(false);
        imagemEsquerda.setVisible(true);
    }
    
    public void setTextoDireito(String texto){
        labelDireita.setText(texto);
        labelDireita.setVisible(true);
        imagemDireita.setVisible(false);
    }
    
    public void setTextoEsquerdo(String texto){
        labelEsquerda.setText(texto);
        imagemEsquerda.setVisible(false);
        labelEsquerda.setVisible(true);
    } 
}
