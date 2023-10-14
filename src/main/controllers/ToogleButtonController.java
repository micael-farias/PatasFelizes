package main.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.utils.ToogleEnum;

public class ToogleButtonController {

    @FXML
    private HBox direito;

    @FXML
    private HBox esquerdo;
    
    @FXML
    private ImageView imagemDireita;

    @FXML
    private ImageView imagemEsquerda;

    
    public void setListeneres(){
      
        direito.setOnMousePressed(e -> {
            direito.setStyle("-fx-background-color: #A8C4F8; -fx-effect: dropshadow(three-pass-box, rgba(22,22,22,0.4), 5, 0, 2, 2);");
            esquerdo.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
        });

        esquerdo.setOnMousePressed(e -> {
            esquerdo.setStyle("-fx-background-color: #A8C4F8; -fx-effect: dropshadow(three-pass-box, rgba(22,22,22,0.4), 5, 0, 2, 2);");
            direito.setStyle("-fx-background-color: #FFFFFF; -fx-effect: none;");
        });
        
    }
    
    public void ativarBotao(ToogleEnum opcao){
        switch (opcao) {
            case DIREITO -> {
                direito.setStyle("-fx-background-color: #A8C4F8; -fx-effect: dropshadow(three-pass-box, rgba(22,22,22,0.4), 5, 0, 2, 2);");
            }
            case ESQUERDO -> {
                esquerdo.setStyle("-fx-background-color: #A8C4F8; -fx-effect: dropshadow(three-pass-box, rgba(22,22,22,0.4), 5, 0, 2, 2);");
            }
            default -> {
            }
        }
    }
    
    public void setImagemDireita(Image imagem){
        imagemDireita.setImage(imagem);
    }
    
    public void setImagemEsquerda(String imagem){
        imagemDireita.setImage(new Image("/assets/images/" + imagem));
    }
    
    public void onClickImagemDireita(EventHandler<MouseEvent> click){
        imagemDireita.setOnMouseClicked(click);
    }
       
    public void onClickImagemEsquerda(EventHandler<MouseEvent> click){
        imagemEsquerda.setOnMouseClicked(click);
    }   
}
