package main.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import main.utils.ToogleEnum;

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

    String shadow = "-fx-effect: dropshadow(three-pass-box, rgba(22, 22, 22, 0.4), 0, 1, 2, 0);";
    
    public void setListeneres(){
      
        direito.setOnMousePressed(e -> {
            direito.setStyle("-fx-background-color: #A8C4F8;" + shadow);
            esquerdo.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
        });

        esquerdo.setOnMousePressed(e -> {
            esquerdo.setStyle("-fx-background-color: #A8C4F8;"  + shadow);
            direito.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
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
    
    public void onClickImagemDireita(EventHandler<MouseEvent> click){
        imagemDireita.setOnMouseClicked(click);
    }
       
    public void onClickImagemEsquerda(EventHandler<MouseEvent> click){
        imagemEsquerda.setOnMouseClicked(click);
    }   
}
