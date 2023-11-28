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
import main.repositories.DoacaoRepository;
import main.services.DoacaoServices;
import main.utils.Constantes;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_DOACOES;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaString;
import static main.utils.DateHelper.DataParaString;
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
   @FXML
   private ImageView iconComprovante;
    
   private Doacao doacao;
   private DoacaoServices doacaoService;

    public void setData(Object[] dados) {
        doacao = (Doacao) ObterDadoArray(dados, 0);
        int posicao = (int) ObterDadoArray(dados, 1);
        
        nomeDoador.setText(doacao.getDoador());
        idDoacao.setText("Doação "+ doacao.getId());
        dataDoacao.setText(DataParaString(doacao.getData()) + "-" +DateHelper.DataParaStringReduced(doacao.getDataCadastro()));
        dataDoacao.setStyle("-fx-font-weight: bold;");
        valorDoacao.setText(RealFormatter.formatarComoReal(doacao.getValor()));
        iconComprovante.setVisible(doacao.getFotoComprovante() != null);
        if(posicao % 2 == 0){
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
          App.getInstance().AbrirDialogComAcao(DIALOG_REMOVER, FORM_DOACOES, contentFather, primaryStage, blackShadow,
                   new Object[]{ "Deseja realmente excluir essa doação? "}, (dados) ->{
                       if(doacaoService.Excluir(doacao.getId()) == 1){
                           App.getInstance().EntrarTelaOnResume(FORM_DOACOES, contentFather, primaryStage, blackShadow, null);
                       }
                   });        
          });  
        
        editarDoacao.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_DOACAO, contentFather, primaryStage, blackShadow,
                   new Object[]{ doacao.getId(), doacao});    
        });
    }
      
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        doacaoService = new DoacaoServices();
        setListeners(contentFather, primmaryStage, blackShadow);    
    }
}
