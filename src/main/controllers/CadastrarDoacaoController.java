package main.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorComDado;
import main.model.Despesa;
import main.model.Doacao;
import main.model.Procedimento;
import main.services.AnimalService;
import main.services.DespesaServices;
import main.services.DoacaoServices;
import main.services.ProcedimentoService;
import main.services.VoluntarioService;
import static main.utils.Constantes.FORM_FINANCAS;
import org.controlsfx.control.textfield.TextFields;

public class CadastrarDoacaoController extends CustomController implements InicializadorComDado{
    
    @FXML
    private DatePicker dataDoacao;

    @FXML
    private TextField doadorDoacao;

    @FXML
    private Button salvarDoacao;

    @FXML
    private TextField tipoDoacao;

    @FXML
    private TextField valorDoacao;
    
    @FXML
    private Button cancelarCadastro;
      
    private DoacaoServices doacaoServices;
    private VoluntarioService voluntarioService;
    private int idDoacao;
    private Doacao doacao;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        initialize(dados);
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(Object[] dados){
        idDoacao = ObterDadoArray(dados, 0) == null ? -1 : (int) ObterDadoArray(dados, 0) ;
        doacao = ObterDadoArray(dados, 1) == null ? null : (Doacao) ObterDadoArray(dados, 1) ;
        
        doacaoServices = new DoacaoServices();
        voluntarioService = new VoluntarioService();
        
        configurarDoadores();
        configurarVoluntarios();
        setData();
    }

    public void configurarDoadores(){
        Set<String> animaisPossiveis = doacaoServices.ObterDoadores();
        TextFields.bindAutoCompletion(doadorDoacao, animaisPossiveis);    
    }  
    
    public void configurarVoluntarios(){
        Set<String> possiveisVoluntarios = voluntarioService.ObterNomeVoluntarios();
        TextFields.bindAutoCompletion(valorDoacao, possiveisVoluntarios);    
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        salvarDoacao.setOnMouseClicked(e->{
            SalvarDespesa();
            App.getInstance().EntrarTelaOnResume(FORM_FINANCAS ,contentFather, primmaryStage, blackShadow, null);                      
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaOnResume(FORM_FINANCAS, contentFather, primmaryStage, blackShadow, null);
        });        
    }
    
    public void SalvarDespesa(){
        LocalDate data = dataDoacao.getValue();
        String doador = doadorDoacao.getText();
        String valor = valorDoacao.getText();
        
        doacaoServices.Salvar(idDoacao, doador, valor, data, new byte[]{});
    }
    
    private void setData() {
        if(doacao != null){
            doadorDoacao.setText(doacao.getDoador());
            LocalDate localDate = doacao.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dataDoacao.setValue(localDate);
            valorDoacao.setText(String.valueOf(doacao.getValor()));
        }
    }
     
    
   
}
