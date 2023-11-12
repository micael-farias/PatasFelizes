package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.model.Voluntario;
import main.model.Procedimento;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateToString.DataParaString;
import static main.utils.ImageLoader.CarregarImagem;
import static main.utils.TelefoneFormatter.FormatarTelefone;

public class VoluntarioController implements Inicializador{

  @FXML
    private ImageView editarVoluntario;

    @FXML
    private Label emailVoluntario;

    @FXML
    private ImageView excluirVoluntario;

    @FXML
    private ImageView fotoVoluntario;

    @FXML
    private HBox layoutVoluntario;

    @FXML
    private Label nomeVoluntario;

    @FXML
    private Label telefoneVoluntario;

    public void setData(int posicao, Object dado) {
        Voluntario voluntario = (Voluntario) dado;
        
         Rectangle clip = new Rectangle(35, 35);
            clip.setArcWidth(10);
            clip.setArcHeight(10);

        CarregarImagem(fotoVoluntario, voluntario.getFoto(), clip);

        nomeVoluntario.setText(voluntario.getNome());
        emailVoluntario.setText(voluntario.getEmail());       
        telefoneVoluntario.setText(FormatarTelefone(voluntario.getTelefone()));

        
        if(posicao % 2 == 0){
            layoutVoluntario.setStyle("-fx-background-color: white;");
            editarVoluntario.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirVoluntario.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {

    }  
}
