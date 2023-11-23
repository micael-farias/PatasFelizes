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
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import main.model.Procedimento;
import main.services.ProcedimentoService;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaString;
import static main.utils.DateHelper.DataParaString;

public class ProcedimentoController extends CustomController implements InicializadorComDado{

    @FXML
    private CheckBox checkBoxRealizado;
    
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
    
    private Procedimento procedimento;
    private ProcedimentoService procedimentoService;
    
    public void setData(Object[] dados) {
        procedimento = (Procedimento) ObterDadoArray(dados, 0);
        procedimentoService = new ProcedimentoService();
        int posicao = (int) ObterDadoArray(dados, 1);
        
        descricaoProcedimento.setText(procedimento.getDescricao());
        dataProcedimento.setText(CalendarParaString(procedimento.getData()));
        dataProcedimento.setStyle("-fx-font-weight: bold;");
        checkBoxRealizado.setSelected(procedimento.isRealizado());
        checkBoxRealizado.setOnAction(event -> {
            if (checkBoxRealizado.isSelected()) {  

                procedimentoService.Salvar(procedimento.getId(),
                        procedimento.getDescricao(), 
                        DateHelper.CalendarParaLocalDate(procedimento.getData()),
                        procedimento.getTipo(),
                        procedimento.getDespesa() == null ? " " : String.valueOf(procedimento.getDespesa().getValor()),
                        procedimento.getVoluntario().getNome(),
                        procedimento.getAnimal().getId(), true);
            }else{
                procedimentoService.Salvar(procedimento.getId(),
                        procedimento.getDescricao(), 
                        DateHelper.CalendarParaLocalDate(procedimento.getData()),
                        procedimento.getTipo(),
                        procedimento.getDespesa() == null ? " " : String.valueOf(procedimento.getDespesa().getValor()),
                        procedimento.getVoluntario().getNome(),
                        procedimento.getAnimal().getId(), false);
            }
        });        
          
        if(posicao % 2 == 0){
            layoutProcedimento.setStyle("-fx-background-color: white;");
            editarProcedimento.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirProcedimento.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }      
    }
    
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow){
        layoutProcedimento.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComOrigemEDado(DIALOG_CADASTRAR_PROCEDIMENTO, FORM_ANIMAL_DETALHES , contentFather, primaryStage, blackShadow,
                    new Object[]{ procedimento.getAnimal().getId(), procedimento});        
            });
    }
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    }
}