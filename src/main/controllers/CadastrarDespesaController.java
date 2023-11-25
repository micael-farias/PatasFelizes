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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorComDado;
import main.model.Despesa;
import main.model.Procedimento;
import main.services.AnimalService;
import main.services.DespesaServices;
import main.services.ProcedimentoService;
import static main.utils.Constantes.FORM_DESPESAS;
import static main.utils.Constantes.FORM_HOME;
import main.utils.RealFormatter;
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
    private TextField valorDespesa;
    
    @FXML
    private Button cancelarCadastro;

    private DespesaServices despesaServices;
    private AnimalService animalServices;
    private ProcedimentoService procedimentoServices;
    private int idDespesa;
    private Despesa despesa;
    
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
        setData();
        
        TextFieldUtils.setupCurrencyTextField(valorDespesa);
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
            SalvarDespesa();
            App.getInstance().EntrarTelaOnResume(FORM_DESPESAS ,contentFather, primmaryStage, blackShadow, null);                      
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });        
    }
    
    public void SalvarDespesa(){
        LocalDate data = dataDespesa.getValue();
        String descriao = descricaoDespesa.getText();
        String pet = petDespesa.getText();
        String tipo = tipoDespesa.getText();
        double valor = RealFormatter.unformatarReal(valorDespesa.getText());
        
        despesaServices.Cadastrar(idDespesa, descriao, valor, data, pet, tipo, null);
    }
    
    private void setData() {
        if(despesa != null){
            Procedimento procedimento = procedimentoServices.ObterProcedimentoPorDespesa(idDespesa);
            descricaoDespesa.setText(despesa.getDescricao());
            LocalDate localDate = despesa.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dataDespesa.setValue(localDate);
            petDespesa.setText((procedimento == null) ? "" : procedimento.getAnimal().getNome());
            tipoDespesa.setText(despesa.getTipo());
            valorDespesa.setText(RealFormatter.formatarComoReal(despesa.getValor()));
        }
    }
}
