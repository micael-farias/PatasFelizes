package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.interfaces.Inicializador;
import main.model.Despesa;
import main.model.Procedimento;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateToString.DataParaString;

public class DespesaController implements Inicializador{

    @FXML
    private HBox layoutDespesa;
    @FXML
    private Label dataDespesa;

    @FXML
    private Label descricaoDespesa;

    @FXML
    private Label valorDespesa;

    @FXML
    private ImageView editarDespesa;

    @FXML
    private ImageView excluirDespesa;

    public void setData(int posicao, Object dado) {
        Despesa despesa = (Despesa) dado;

        descricaoDespesa.setText(despesa.getDescricao());
        dataDespesa.setText(DataParaString(despesa.getData()));
        dataDespesa.setStyle("-fx-font-weight: bold;");

        
        if(posicao % 2 == 0){
            layoutDespesa.setStyle("-fx-background-color: white;");
            editarDespesa.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirDespesa.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {

    }  
}
