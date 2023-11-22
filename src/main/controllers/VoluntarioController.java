package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import main.model.Voluntario;
import static main.utils.Constantes.DIALOG_CADASTRAR_VOLUNTARIO;
import static main.utils.ImageLoader.CarregarImagem;

public class VoluntarioController extends CustomController implements InicializadorComDado{

    @FXML
    private ImageView fotoVoluntario;

    @FXML
    private VBox layoutVoluntario;

    @FXML
    private Label nomeVoluntario;

    @FXML
    private Label telefoneVoluntario;
    
    private Voluntario voluntario;

    public void setData(Object[] dado) {
        voluntario = (Voluntario) ObterDadoArray(dado, 0);
             
        Rectangle clip = new Rectangle(170, 170);
        clip.setArcWidth(180);
        clip.setArcHeight(180);

        CarregarImagem(fotoVoluntario, voluntario.getFoto(), voluntario.idFoto(), clip);
        String nome = voluntario.getNome();

        nomeVoluntario.setText(nome);
        nomeVoluntario.setStyle("-fx-font-weight: bold; -fx-font: 14pt \"Helvetica\";-fx-text-fill: #000000;");
        telefoneVoluntario.setStyle("    -fx-font-weight: bold;   -fx-font: 10pt \"Noto Sans Mono Bold\"; -fx-text-fill: #888888;");    
    }

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);
    }  
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        layoutVoluntario.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_VOLUNTARIO, contentFather, primmaryStage, blackShadow, new Object[]{ voluntario.getId(), voluntario });
        });        
    }
}
