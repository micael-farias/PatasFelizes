package main.controllers;

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
import main.model.Procedimento;
import main.services.AnimalService;
import main.services.DespesaServices;
import main.services.TarefaServices;
import main.services.VoluntarioService;
import main.utils.DateHelper;
import static main.utils.TextFieldUtils.capitalizeEachWord;
import main.views.textfield.ChoiceBoxCostumized;
import org.controlsfx.control.textfield.TextFields;

public class FiltrarTarefaController implements InicializadorComAcao {

    @FXML
    private Button cancelarFiltro;

    @FXML
    private DatePicker dataFinalDatePicker;

    @FXML
    private DatePicker dataInicialDatePicker;

    @FXML
    private Button filtrar;

    @FXML
    private TextField petTextField;

    @FXML
    private ChoiceBox<String> tiposOrdenacaoChoiceBox;
    private ChoiceBoxCostumized ordenacaoChoiceBox;

    @FXML
    private TextField voluntariosTextField;

    private static FiltroDespesa filtro;
    private Acao acao;
    private VoluntarioService voluntarioService;
    private AnimalService animalService;
    private TarefaServices tarefasService;
    
    @Override
    public void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao, Object[] dados) {
       this.acao = acao;
       inicializa();
       configuraTextFields();
       setListeners(primmaryStage, blackShadow);
    
       if(filtro != null) setFiltros();
    }
    
    void getFiltros(){
        filtro = (filtro == null) ? new FiltroDespesa() : filtro;
        filtro.setAnimal(petTextField.getText());
        filtro.setVoluntario(voluntariosTextField.getText());
        filtro.setDataFinal(DateHelper.LocalDateParaCalendar(dataFinalDatePicker.getValue()));
        filtro.setDataInicial(DateHelper.LocalDateParaCalendar(dataInicialDatePicker.getValue()));
        filtro.setOrdenacao(Mapping.GetKeyOrdenacoesDespesaDoacoes(ordenacaoChoiceBox.getValue()));      
    }
    
    private void inicializa(){
        voluntarioService = new VoluntarioService();
        animalService = new AnimalService();
        tarefasService = new TarefaServices();
        tiposOrdenacaoChoiceBox.setItems(Mapping.getOrdenacoes());
        ordenacaoChoiceBox = new ChoiceBoxCostumized();
        ordenacaoChoiceBox.initialize(tiposOrdenacaoChoiceBox);
    }
    
    public void configurarAnimais(){
       Set<String> voluntariosPossiveis = animalService.ObterNomesAnimais();
       TextFields.bindAutoCompletion(petTextField, voluntariosPossiveis);   
    }
    
    public void configurarVoluntarios(){
       Set<String> voluntariosPossiveis = voluntarioService.ObterNomeVoluntarios();
       TextFields.bindAutoCompletion(voluntariosTextField, voluntariosPossiveis);     
    }
    
    public void configuraTextFields(){
        configurarVoluntarios();
        configurarAnimais();
    }
    
    private List<Procedimento> FiltrarProcedimentos()
    {
        getFiltros();
        
        if((filtro.getDataInicial() == null) !=  (filtro.getDataFinal() == null)){
           App.getInstance().SetMensagem(MensagemTipo.ERRO, "Voce deve selecionar um intervalo de datas", null);
            return null;
        }
        
        if(filtro.getDataFinal()!= null && filtro.getDataFinal().before(filtro.getDataInicial())){
           App.getInstance().SetMensagem(MensagemTipo.ERRO, "A data final deve ser maior que a inicial", null);
           return null;
        }
        return tarefasService.FiltrarTarefas(filtro);    
    }
    
    private void setFiltros(){
        if(filtro.getDataFinal() != null)
            dataFinalDatePicker.setValue(DateHelper.CalendarParaLocalDate(filtro.getDataFinal()));
        
        if(filtro.getDataInicial() != null)
            dataInicialDatePicker.setValue(DateHelper.CalendarParaLocalDate(filtro.getDataInicial()));
        
        petTextField.setText(filtro.getAnimal());
        voluntariosTextField.setText(filtro.getVoluntario());
        tiposOrdenacaoChoiceBox.setValue(Mapping.GetKeyByValue(Mapping.getOrdenacoesDespesaDoacoesHash(), filtro.getOrdenacao()));       
    }
    
    private void setListeners(Stage primmaryStage, Pane blackShadow){
        filtrar.setOnMouseClicked(e ->{
            var despesas = FiltrarProcedimentos();
            if(despesas != null){
                 acao.RealizarAcao(new Object[]{ despesas, filtro });
                 App.getInstance().FecharDialog(primmaryStage, blackShadow);
            }       
        });      
        
        cancelarFiltro.setOnMouseClicked(e ->{
           App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });
        
        capitalizeEachWord(voluntariosTextField);
        capitalizeEachWord(petTextField);
    }  
}
