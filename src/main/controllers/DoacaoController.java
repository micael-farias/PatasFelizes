package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.model.Doacao;
import main.model.Procedimento;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateToString.DataParaString;

public class DoacaoController implements Inicializador{

    @FXML
    private Label dataDoacao;

    @FXML
    private VBox doacaoLayout;

    @FXML
    private ImageView editarDoacao;

    @FXML
    private ImageView excluirDoacao;

    @FXML
    private Label idDoacao;

    @FXML
    private HBox layoutDoacao;

    @FXML
    private Label nomeDoador;

    @FXML
    private Label valorDoacao;

    public void setData(int posicao, Object dado) {
        Doacao doacao = (Doacao) dado;

        nomeDoador.setText(doacao.getDoador());
        idDoacao.setText("Doação "+ doacao.getId());
        dataDoacao.setText(DataParaString(doacao.getData()));
        dataDoacao.setStyle("-fx-font-weight: bold;");

        
        if(posicao % 2 == 0){
            layoutDoacao.setStyle("-fx-background-color: white;");
            editarDoacao.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirDoacao.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {

    }  
}
