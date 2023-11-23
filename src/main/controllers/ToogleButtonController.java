package main.controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import main.utils.ToogleEnum;

public class ToogleButtonController {

    
    @FXML
    private ImageView imagemDireita;

    @FXML
    private ImageView imagemEsquerda;

    @FXML
    private Label labelDireita;

    @FXML
    private Label labelEsquerda;

    @FXML
    private HBox layoutCheckBox;

    @FXML
    private Pane paneAtual;

    @FXML
    private StackPane direito;

    @FXML
    private StackPane esquerdo;
    
    public ToogleEnum lado;
    
    private boolean toogleDesativado;
    
    String shadow = "-fx-effect: dropshadow(three-pass-box, rgba(22, 22, 22, 0.4), 0, 1, 2, 0);";
    
    public void setListeneres(EventHandler<MouseEvent> clickDireita, EventHandler<MouseEvent> clickEsquerda){
      
        direito.setOnMousePressed(e -> {
            if(toogleDesativado) return;
            moverParaEsquerda();
            ativarBotao(ToogleEnum.DIREITO);
            if (clickDireita != null) {
                MouseEvent event = new MouseEvent(direito, direito, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, false, false, false, null);
                clickDireita.handle(event);
            } 
        });

        esquerdo.setOnMousePressed(e -> {
            if(toogleDesativado) return;
            moverParaDireita();
            ativarBotao(ToogleEnum.ESQUERDO);
            if (clickDireita != null) {
                MouseEvent event = new MouseEvent(esquerdo, esquerdo, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false, true, false, false, false, false, false, null);
                clickEsquerda.handle(event);
            } 
        });
        
    }
    
    public void desativarToogle(){
        layoutCheckBox.setStyle("-fx-background-color: gray;");
        direito.setStyle("-fx-background-color: gray;");
        esquerdo.setStyle("-fx-background-color: gray;");
        toogleDesativado = true;
    }
    
    public void ativarToogle(){
        layoutCheckBox.setStyle("-fx-background-color: #FFFFFF;");
        direito.setStyle("-fx-background-color: #A8C4F8;");
        esquerdo.setStyle("-fx-background-color: #FFFFFF;");
        toogleDesativado = false;
    }
    
    public void ativarBotao(ToogleEnum opcao){
        switch (opcao) {
            case DIREITO -> {
                lado = ToogleEnum.DIREITO;
            }
            case ESQUERDO -> {
                lado = ToogleEnum.ESQUERDO;
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

    private void moverParaEsquerda() {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), paneAtual);
            transition.setToX(layoutCheckBox.getWidth() - paneAtual.getWidth());
            transition.play(); 
    }

    private void moverParaDireita() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), paneAtual);
        transition.setToX(0);
        transition.play();
    }
    
    public ToogleEnum getSelectedItem() {
        return lado;
    }
}
