package main.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.Calendar;
import main.App;
import main.enums.MensagemTipo;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import main.model.Procedimento;
import main.services.ProcedimentoService;
import main.utils.Constantes;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.FORM_HOME;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaString;
import static main.utils.DateHelper.DataParaString;

public class ProcedimentoController extends CustomController implements InicializadorComDado{

    @FXML
    private ImageView checkBoxRealizado;

    @FXML
    private Label dataProcedimento;

    @FXML
    private Label descricaoProcedimento;

    @FXML
    private ImageView editarProcedimento;

    @FXML
    private ImageView excluirProcedimento;

    @FXML
    private HBox layoutClickable;

    @FXML
    private HBox layoutProcedimento;

    @FXML
    private Label voluntarioProcedimento;
    
    private Procedimento procedimento;
    
    private ProcedimentoService procedimentoService;
       
    public void setImage(boolean realizado){
        if(realizado){
            checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_cinza_checked.png"));          
        }else{
            checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_cinza_not_checked.png"));           
        }        
    }
    
    public void setData(Object[] dados) {
        procedimento = (Procedimento) ObterDadoArray(dados, 0);
        procedimentoService = new ProcedimentoService();
        int posicao = (int) ObterDadoArray(dados, 1);
        
        descricaoProcedimento.setText(procedimento.getDescricao());
        dataProcedimento.setText(CalendarParaString(procedimento.getData()));
        setImage(procedimento.isRealizado());

        
        checkBoxRealizado.setOnMouseClicked(event -> {
            boolean realizada = !procedimento.isRealizado();
            setImage(realizada);
            Procedimento procedimentoRetornado;
            if (realizada) {  

                procedimentoRetornado = procedimentoService.Salvar(procedimento.getId(),
                        procedimento.getDescricao(), 
                        DateHelper.CalendarParaLocalDate(procedimento.getData()),
                        procedimento.getTipo(),
                        procedimento.getDespesa() == null ? 0.0 : procedimento.getDespesa().getValor(),
                        procedimento.getVoluntario() == null? null : procedimento.getVoluntario().getNome(),
                        procedimento.getAnimal().getId(), true);
            }else{
                 procedimentoRetornado = procedimentoService.Salvar(procedimento.getId(),
                        procedimento.getDescricao(), 
                        DateHelper.CalendarParaLocalDate(procedimento.getData()),
                        procedimento.getTipo(),
                        procedimento.getDespesa() == null ? 0.0 : procedimento.getDespesa().getValor(),
                        procedimento.getVoluntario() == null? null : procedimento.getVoluntario().getNome(),
                        procedimento.getAnimal().getId(), false);
            }
             if(procedimentoRetornado == null){
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao alterar o estado do procedimento", null);
            }else{
                procedimento= procedimentoRetornado;
            }
        });        
          
        if(posicao % 2 == 0){
            layoutProcedimento.setStyle("-fx-background-color: white;");
            editarProcedimento.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirProcedimento.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }      
    }
    
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow){
        layoutClickable.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_PROCEDIMENTO, contentFather, primaryStage, blackShadow,
                    new Object[]{ procedimento.getAnimal().getId(), procedimento});        
            });
        
        excluirProcedimento.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComAcao(DIALOG_REMOVER, FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow,
                   new Object[]{ "Deseja realmente excluir esse procedimento? "}, (dado) ->{
                       if(procedimentoService.Excluir(procedimento.getId()) == 1){
                           App.getInstance().EntrarTelaOnResume(FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow, new Object[] {procedimento.getAnimal()});
                       }
                   });        
          });  
        
        editarProcedimento.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_PROCEDIMENTO , contentFather, primaryStage, blackShadow,
                   new Object[]{ procedimento.getId(), procedimento});    
        });
    }
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    }
}