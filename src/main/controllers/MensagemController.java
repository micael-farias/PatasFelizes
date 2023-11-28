package main.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import main.enums.MensagemTipo;
import static main.utils.Constantes.PATH_IMAGES;

public class MensagemController {

    @FXML
    private ImageView iconMensagem;

    @FXML
    private AnchorPane layoutMensagem;

    @FXML
    private Label mensagem;


    public void setData(MensagemTipo tipo, String msg) {
        String estilosExistentes = layoutMensagem.getStyle();

        String estiloFundo = (tipo == MensagemTipo.ERRO) ? "-fx-background-color: #8C2222;" : "-fx-background-color: #228B22;";

        String novoEstilo = estilosExistentes + estiloFundo;
        layoutMensagem.setStyle(novoEstilo);

        String iconePath = (tipo == MensagemTipo.ERRO) ? PATH_IMAGES + "sistemaVermelho.png" : PATH_IMAGES + "sistemaVerde.png";
        Image icone = new Image(getClass().getResourceAsStream(iconePath));
        iconMensagem.setImage(icone);
        
        mensagem.setText(msg);
        
        // Ajuste a altura da Label mensagem para se adequar ao conteúdo
        mensagem.setPrefHeight(USE_COMPUTED_SIZE);
        mensagem.setMinHeight(Region.USE_PREF_SIZE);
        mensagem.setMaxHeight(USE_COMPUTED_SIZE);
    }
}
