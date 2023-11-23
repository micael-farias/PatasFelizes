package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import main.model.Despesa;
import main.model.Procedimento;
import main.services.DespesaServices;
import main.services.ProcedimentoService;
import static main.utils.Constantes.DIALOG_CADASTRAR_DESPESA;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaString;
import static main.utils.DateHelper.DataParaString;
import main.utils.RealFormatter;

public class DespesaController extends CustomController implements InicializadorComDado{

    @FXML
    private HBox layoutDespesa;
    
    @FXML
    private CheckBox checkBoxRealizado;
    
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
    
    private Despesa despesa;
    private DespesaServices despesaService;
    private ProcedimentoService procedimentoService;

    public void setData(Object[] dados) {
        despesaService = new DespesaServices();
        procedimentoService = new ProcedimentoService();
        
        despesa = (Despesa) ObterDadoArray(dados, 0);
        int posicao = (int) ObterDadoArray(dados, 1);
        
        descricaoDespesa.setText(despesa.getDescricao());
        dataDespesa.setText(CalendarParaString(despesa.getData()));
        dataDespesa.setStyle("-fx-font-weight: bold;");
        valorDespesa.setText(RealFormatter.formatarComoReal(despesa.getValor()));
        checkBoxRealizado.setSelected(despesa.isRealizada());
        checkBoxRealizado.setOnAction(event -> {
            if (checkBoxRealizado.isSelected()) {         
                despesaService.Cadastrar(despesa.getId(), despesa.getDescricao(), String.valueOf(despesa.getValor()),
                        DateHelper.CalendarParaLocalDate(despesa.getData()), ObterAnimalDespesa(), despesa.getTipo(), true);
            }else{
                despesaService.Cadastrar(despesa.getId(), despesa.getDescricao(), String.valueOf(despesa.getValor()),
                        DateHelper.CalendarParaLocalDate(despesa.getData()), ObterAnimalDespesa(), despesa.getTipo(), false);      
            }
        });        
        
        if(posicao % 2 == 0){
            layoutDespesa.setStyle("-fx-background-color: white;");
            editarDespesa.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirDespesa.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }
    
    public String ObterAnimalDespesa(){
        Procedimento procedimento = procedimentoService.ObterProcedimentoPorDespesa(despesa.getId());
        if(procedimento != null){
            return procedimento.getAnimal().getNome();
        }
        return null;
    }
    
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow){
        layoutDespesa.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_DESPESA , contentFather, primaryStage, blackShadow,
                    new Object[]{ despesa.getId(), despesa});        
            });
    }
      
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    
    }
}