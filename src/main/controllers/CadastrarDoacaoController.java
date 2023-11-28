package main.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
import static main.utils.Constantes.FORM_DOACOES;
import main.utils.ImageLoader;
import main.utils.PdfDownloader;
import main.utils.RealFormatter;
import main.utils.TextFieldUtils;
import main.utils.ValidacaoUtils;
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
    private HBox layoutAdicionarComprovante;

    @FXML
    private HBox layoutComprovante;

    @FXML
    private Label labelComprovante;
    @FXML
    private Button cancelarCadastro;
    
    private DoacaoServices doacaoServices;
    private VoluntarioService voluntarioService;
    private int idDoacao;
    private Doacao doacao;
    private byte[] comprovante;

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
        TextFieldUtils.setupCurrencyTextField(valorDoacao);
        
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
            if(SalvarDespesa() != null)
                App.getInstance().EntrarTelaOnResume(FORM_DOACOES ,contentFather, primmaryStage, blackShadow, null);                      
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaNoAction(FORM_DOACOES, contentFather, primmaryStage, blackShadow);
        });      
          layoutAdicionarComprovante.setOnMouseClicked(e ->{
            comprovante = ImageLoader.CarregarImagemLocal(primmaryStage);
            labelComprovante.setText("doacao_"+idDoacao+".pdf");
            layoutAdicionarComprovante.setVisible(false);
            layoutComprovante.setVisible(true);
        });
        
        layoutComprovante.setOnMouseClicked(e -> {
                PdfDownloader.baixarPdf(doacao.getFotoComprovante(), "doacao_"+idDoacao+".pdf");

        });
    }
    
    public Doacao SalvarDespesa(){
        LocalDate data = dataDoacao.getValue();
        String doador = doadorDoacao.getText();
        double valor = RealFormatter.unformatarReal(valorDoacao.getText());
        
        if(!validaDoacao(doador, doador, valor)) return null;
        
        return doacaoServices.Salvar(idDoacao, doador, valor, data, comprovante);
    }
    
    private void setData() {
        if(doacao != null){
            doadorDoacao.setText(doacao.getDoador());
            LocalDate localDate = doacao.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dataDoacao.setValue(localDate);
            var valor = RealFormatter.formatarComoReal(doacao.getValor());
                    valorDoacao.setText(valor);
              comprovante = doacao.getFotoComprovante();
            if(comprovante != null){
                layoutComprovante.setVisible(true);
                labelComprovante.setText("doacao_"+idDoacao+".pdf");
                layoutAdicionarComprovante.setVisible(false);
            }
        }
    }
    
      
    public boolean validaDoacao(String descricao, String tipo, double valor){
        boolean doador = ValidacaoUtils.validarCampo(descricao, doadorDoacao, "O nome do doador não deve ser vazio");
        boolean dataValida = ValidacaoUtils.validarCampo(dataDoacao);
        boolean valorValido = ValidacaoUtils.validarCampoReal(valor, valorDoacao, "Um valor deve ser informado");
        return doador && dataValida && valorValido;
    }
     
    
   
}
