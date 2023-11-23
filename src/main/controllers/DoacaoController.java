package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorComDado;
import main.model.Doacao;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_FINANCAS;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateHelper.CalendarParaString;
import main.utils.RealFormatter;

public class DoacaoController extends CustomController implements InicializadorComDado{

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
    @FXML
    private HBox layoutClickable;
   
    private Doacao doacao;


    public void setData(Object[] dados) {
        doacao = (Doacao) ObterDadoArray(dados, 0);
        int posicao = (int) ObterDadoArray(dados, 1);
        
        nomeDoador.setText(doacao.getDoador());
        idDoacao.setText("Doação "+ doacao.getId());
        dataDoacao.setText(CalendarParaString(doacao.getData()));
        dataDoacao.setStyle("-fx-font-weight: bold;");
        valorDoacao.setText(RealFormatter.formatarComoReal(doacao.getValor()));

        if(posicao % 2 != 0){
            layoutDoacao.setStyle("-fx-background-color: white;");
            editarDoacao.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirDoacao.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }

      public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow){
        layoutClickable.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_DOACAO, contentFather, primaryStage, blackShadow,
                    new Object[]{ doacao.getId(), doacao});        
            });
        
        excluirDoacao.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComOrigemEDado(DIALOG_REMOVER, FORM_FINANCAS, contentFather, primaryStage, blackShadow,
                   new Object[]{ "Deseja realmente excluir essa doação? "});        
          });  
        
        editarDoacao.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_DOACAO, contentFather, primaryStage, blackShadow,
                   new Object[]{ doacao.getId(), doacao});    
        });
    }
      
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    
    }
}
