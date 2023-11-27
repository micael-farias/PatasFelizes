package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.enums.MensagemTipo;
import main.interfaces.InicializadorComDado;
import main.model.Despesa;
import main.model.Procedimento;
import main.services.DespesaServices;
import main.services.ProcedimentoService;
import static main.utils.Constantes.DIALOG_CADASTRAR_DESPESA;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_DOACOES;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaString;
import main.utils.RealFormatter;

public class DespesaController extends CustomController implements InicializadorComDado{

    @FXML
    private HBox layoutClickable;

    @FXML
    private HBox layoutDespesa;
    
    @FXML
    private ImageView checkBoxRealizado;
    
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
        
    @FXML
    private ImageView iconComprovante;
    
    
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
        valorDespesa.setText(RealFormatter.formatarComoReal(despesa.getValor()));
        iconComprovante.setVisible(despesa.getFotoComprovante() != null);
        setImage(despesa.isRealizada());

        
        checkBoxRealizado.setOnMouseClicked(event -> {
            boolean realizada = !despesa.isRealizada();
            setImage(realizada);
            Despesa despesaObtida;
            if (realizada) {  
                despesaObtida = despesaService.Cadastrar(despesa.getId(), despesa.getDescricao(), despesa.getValor(),
                        DateHelper.CalendarParaLocalDate(despesa.getData()), ObterAnimalDespesa(), despesa.getTipo(), true, despesa.getFotoComprovante());
            }else{
                despesaObtida = despesaService.Cadastrar(despesa.getId(), despesa.getDescricao(), despesa.getValor(),
                        DateHelper.CalendarParaLocalDate(despesa.getData()), ObterAnimalDespesa(), despesa.getTipo(), false, despesa.getFotoComprovante());      
            }
            if(despesaObtida == null){
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao alterar o estado da despesa");
            }else{
                despesa= despesaObtida;
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
        layoutClickable.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_DESPESA , contentFather, primaryStage, blackShadow,
                   new Object[]{ despesa.getId(), despesa});        
          });
        
        excluirDespesa.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComOrigemEDado(DIALOG_REMOVER, FORM_DOACOES, contentFather, primaryStage, blackShadow,
                   new Object[]{ "Deseja realmente excluir essa despesa? "});        
          });  
        
        editarDespesa.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_DESPESA , contentFather, primaryStage, blackShadow,
                   new Object[]{ despesa.getId(), despesa});    
        });
    }
      
    public void setImage(boolean realizado){

            if(realizado){
                checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_cinza_checked.png"));          
            }else{
                checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_cinza_not_checked.png"));           
            }
        
    }
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    
    }
}