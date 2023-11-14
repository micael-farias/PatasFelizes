package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.model.Voluntario;
import static main.utils.ImageLoader.CarregarImagem;

public class VoluntarioController implements Inicializador{

    @FXML
    private ImageView fotoVoluntario;

   // @FXML
    //private HBox layoutVoluntario;

    @FXML
    private Label nomeVoluntario;

    @FXML
    private Label telefoneVoluntario;

    public void setData(int posicao, Object dado) {
        Voluntario voluntario = (Voluntario) dado;
        
       
        Rectangle clip = new Rectangle(170, 170);
        clip.setArcWidth(180);
        clip.setArcHeight(180);

        CarregarImagem(fotoVoluntario, voluntario.getFoto(), clip);
        String nome = voluntario.getNome();
        /*if(nome.length() > 10){
            nome = nome.substring(0,10);
        }*/
        nomeVoluntario.setText(nome);
        nomeVoluntario.setStyle("-fx-font-weight: bold; -fx-font: 14pt \"Helvetica\";-fx-text-fill: #000000;");
        telefoneVoluntario.setStyle("    -fx-font-weight: bold;   -fx-font: 10pt \"Noto Sans Mono Bold\"; -fx-text-fill: #888888;");
       /* emailVoluntario.setText(voluntario.getEmail());       
        telefoneVoluntario.setText(FormatarTelefone(voluntario.getTelefone()));

        
        if(posicao % 2 == 0){
            layoutVoluntario.setStyle("-fx-background-color: white;");
            editarVoluntario.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirVoluntario.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }     */      
    }

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {

    }  
}
