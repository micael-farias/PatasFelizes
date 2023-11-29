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
import static main.utils.Rectangles.GetCircleVoluntario;
import static main.utils.Rectangles.GetRectangleVoluntario;
import static main.utils.TextFieldUtils.formatarTelefone;
import main.utils.ValidacaoUtils;

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
            
        CarregarImagem(fotoVoluntario, voluntario.getFoto(), voluntario.idFoto(), GetCircleVoluntario());
        String nome = voluntario.getNome();
        nomeVoluntario.setText(nome);
        telefoneVoluntario.setText(formatarTelefone(voluntario.getTelefone()));
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
