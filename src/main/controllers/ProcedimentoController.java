package main.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import main.model.Procedimento;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateHelper.DataParaString;

public class ProcedimentoController {

    @FXML
    private VBox adicionarPet;

    @FXML
    private Label dataProcedimento;

    @FXML
    private Label descricaoProcedimento;

    @FXML
    private ImageView editarProcedimento;

    @FXML
    private ImageView excluirProcedimento;
    
    @FXML
    private HBox layoutProcedimento;
    
    public void setData(int posicao, Object dado) {
        Procedimento procedimento = (Procedimento) dado;

        descricaoProcedimento.setText(procedimento.getDescricao());
        dataProcedimento.setText(DataParaString(procedimento.getData()));
        dataProcedimento.setStyle("-fx-font-weight: bold;");
       

        
        if(posicao % 2 == 0){
            layoutProcedimento.setStyle("-fx-background-color: white;");
            editarProcedimento.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirProcedimento.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }      
    }

 

}