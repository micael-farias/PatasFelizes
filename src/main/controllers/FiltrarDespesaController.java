package main.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.enums.Mapping;
import main.enums.MensagemTipo;
import main.interfaces.Acao;
import main.interfaces.InicializadorComAcao;
import main.model.Despesa;
import main.model.FiltroDespesa;
import main.services.AnimalService;
import main.services.DespesaServices;
import main.utils.DateHelper;
import main.views.textfield.ChoiceBoxCostumized;
import org.controlsfx.control.textfield.TextFields;

public class FiltrarDespesaController implements InicializadorComAcao {
    
    @FXML
    private DatePicker dataFinalDatePicker;

    @FXML
    private DatePicker dataInicialDatePicker;

    @FXML
    private TextField petTextField;

    @FXML
    private TextField tiposDespesaTextField;

    @FXML
    private ChoiceBox<String> tiposOrdenacaoChoiceBox;
    
    private ChoiceBoxCostumized ordenacaoChoiceBox;

        
    @FXML
    private Button filtrar, cancelarFiltro;
    
    private Acao acao;
    private DespesaServices despesaService;
    private AnimalService animalService;
    
    @Override
    public void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao, Object[] dados) {
       this.acao = acao;
       inicializa();
       configuraTextFields();
       setListeners(primmaryStage, blackShadow);
    
       if(despesaService.filtro != null) setFiltros();
    }
    
    void getFiltros(){
        despesaService.filtro = (despesaService.filtro == null) ? new FiltroDespesa() : despesaService.filtro;
        despesaService.filtro.setAnimal(petTextField.getText());
        despesaService.filtro.setTipo(tiposDespesaTextField.getText());
        despesaService.filtro.setDataFinal(DateHelper.LocalDateParaCalendar(dataFinalDatePicker.getValue()));
        despesaService.filtro.setDataInicial(DateHelper.LocalDateParaCalendar(dataInicialDatePicker.getValue()));
        despesaService.filtro.setOrdenacao(Mapping.GetKeyOrdenacoesDespesaDoacoes(ordenacaoChoiceBox.getValue()));      
    }
    
    private void inicializa(){
        despesaService = new DespesaServices();
        animalService = new AnimalService();
        tiposOrdenacaoChoiceBox.setItems(Mapping.getOrdenacoesDespesaDoacoes());
        ordenacaoChoiceBox = new ChoiceBoxCostumized();
        ordenacaoChoiceBox.initialize(tiposOrdenacaoChoiceBox);
    }
    
    public void configurarVoluntarios(){
       Set<String> voluntariosPossiveis = animalService.ObterNomesAnimais();
       TextFields.bindAutoCompletion(petTextField, voluntariosPossiveis);   
    }
    
    public void configurarTiposDespesa(){
       Set<String> voluntariosPossiveis = despesaService.ObterTiposDespesa();
       TextFields.bindAutoCompletion(tiposDespesaTextField, voluntariosPossiveis);     
    }
    
    public void configuraTextFields(){
        configurarVoluntarios();
        configurarTiposDespesa();
    }
    
    private List<Despesa> FiltrarDespesas()
    {
        getFiltros();
        
        if((despesaService.filtro.getDataInicial() == null) !=  (despesaService.filtro.getDataFinal() == null)){
           App.getInstance().SetMensagem(MensagemTipo.ERRO, "Voce deve selecionar um intervalo de datas");
        }
        
        if(despesaService.filtro.getDataFinal()!= null && despesaService.filtro.getDataFinal().before(despesaService.filtro.getDataInicial())){
           App.getInstance().SetMensagem(MensagemTipo.ERRO, "A data final deve ser maior que a inicial");
           return null;
        }
        
        return despesaService.FiltrarDespesas(despesaService.filtro);    
    }
    
    void setFiltros(){
        if(despesaService.filtro.getDataFinal() != null)
            dataFinalDatePicker.setValue(DateHelper.CalendarParaLocalDate(despesaService.filtro.getDataFinal()));
        
        if(despesaService.filtro.getDataInicial() != null)
            dataInicialDatePicker.setValue(DateHelper.CalendarParaLocalDate(despesaService.filtro.getDataInicial()));
        petTextField.setText(despesaService.filtro.getAnimal());
        tiposDespesaTextField.setText(despesaService.filtro.getTipo());     
        tiposOrdenacaoChoiceBox.setValue(Mapping.GetKeyByValue(Mapping.getOrdenacoesDespesaDoacoesHash(), despesaService.filtro.getOrdenacao()));       
    }
    
    void setListeners(Stage primmaryStage, Pane blackShadow){
        filtrar.setOnMouseClicked(e ->{
            var despesas = FiltrarDespesas();
            if(despesas != null){
                 acao.RealizarAcao(new Object[]{ despesas, despesaService.filtro });
                 App.getInstance().FecharDialog(primmaryStage, blackShadow);
            }       
        });      
        
        cancelarFiltro.setOnMouseClicked(e ->{
           App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });
    }

   
    
}
