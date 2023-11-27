package main.controllers;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import static main.controllers.AnimalFormularioController.CarregarImagem;
import main.interfaces.InicializadorComDado;
import main.model.Despesa;
import main.model.Procedimento;
import main.services.AnimalService;
import main.services.DespesaServices;
import main.services.ProcedimentoService;
import static main.utils.Constantes.FORM_DESPESAS;
import static main.utils.Constantes.FORM_HOME;
import main.utils.ImageLoader;
import main.utils.PdfDownloader;
import main.utils.RealFormatter;
import main.utils.Rectangles;
import main.utils.TextFieldUtils;
import org.controlsfx.control.textfield.TextFields;

public class CadastrarDespesaController extends CustomController implements InicializadorComDado{

    @FXML
    private DatePicker dataDespesa;

    @FXML
    private TextArea descricaoDespesa;

    @FXML
    private TextField petDespesa;
   
    @FXML
    private Button salvarDespesa;

    @FXML
    private TextField tipoDespesa;

    @FXML
    private Label labelComprovante;
    
    @FXML
    private TextField valorDespesa;
    
    @FXML
    private Button cancelarCadastro;
    
    @FXML
    private HBox layoutAdicionarComprovante;

    @FXML
    private HBox layoutComprovante;

    private DespesaServices despesaServices;
    private AnimalService animalServices;
    private ProcedimentoService procedimentoServices;
    private int idDespesa;
    private Despesa despesa;
    private byte[] comprovante;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        initialize(dados);
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(Object[] dados){
        idDespesa = ObterDadoArray(dados, 0) == null ? -1 : (int) ObterDadoArray(dados, 0) ;
        despesa = ObterDadoArray(dados, 1) == null ? null : (Despesa) ObterDadoArray(dados, 1) ;
        
        animalServices = new AnimalService();
        despesaServices = new DespesaServices();
        procedimentoServices = new ProcedimentoService();

        configurarPets();
        configurarTiposDespesa();
        
        TextFieldUtils.setupCurrencyTextField(valorDespesa);
         setData();
   }

    public void configurarPets(){
        Set<String> animaisPossiveis = animalServices.ObterNomesAnimais();
        TextFields.bindAutoCompletion(petDespesa, animaisPossiveis);    
    }  
    
    public void configurarTiposDespesa(){
        Set<String> possiveisTipoDespesa = despesaServices.ObterTiposDespesa();
        TextFields.bindAutoCompletion(tipoDespesa, possiveisTipoDespesa);    
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        salvarDespesa.setOnMouseClicked(e->{
            if(SalvarDespesa() != null)
                App.getInstance().EntrarTelaOnResume(FORM_DESPESAS ,contentFather, primmaryStage, blackShadow, null);                      
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });        
        
        layoutAdicionarComprovante.setOnMouseClicked(e ->{
            comprovante = ImageLoader.CarregarImagemLocal(primmaryStage);
            labelComprovante.setText("despesa_"+idDespesa+".pdf");
            layoutAdicionarComprovante.setVisible(false);
            layoutComprovante.setVisible(true);
        });
        
        layoutComprovante.setOnMouseClicked(e -> {
                PdfDownloader.baixarPdf(despesa.getFotoComprovante(), "despesa_"+idDespesa+".pdf");

        });
        
        
    }
    
    public Despesa SalvarDespesa(){
        LocalDate data = dataDespesa.getValue();
        String descriao = descricaoDespesa.getText();
        String pet = petDespesa.getText();
        String tipo = tipoDespesa.getText();
        double valor = RealFormatter.unformatarReal(valorDespesa.getText());
        
        return despesaServices.Cadastrar(idDespesa, descriao, valor, data, pet, tipo, null, comprovante);
    }
    
    private void setData() {
        if(despesa != null){
            Procedimento procedimento = procedimentoServices.ObterProcedimentoPorDespesa(idDespesa);
            descricaoDespesa.setText(despesa.getDescricao());
            LocalDate localDate = despesa.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dataDespesa.setValue(localDate);
            petDespesa.setText((procedimento == null) ? "" : procedimento.getAnimal() == null ? null : procedimento.getAnimal().getNome());
            tipoDespesa.setText(despesa.getTipo());
            valorDespesa.setText(RealFormatter.formatarComoReal(despesa.getValor()));
            comprovante = despesa.getFotoComprovante();
            if(comprovante != null){
                layoutComprovante.setVisible(true);
                labelComprovante.setText("despesa_"+idDespesa+".pdf");
                layoutAdicionarComprovante.setVisible(false);
            }
        }
    }
}
