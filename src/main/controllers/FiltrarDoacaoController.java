package main.controllers;

import java.time.LocalDate;
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
import main.model.Doacao;
import main.model.FiltroDespesa;
import main.model.Procedimento;
import main.services.DoacaoServices;
import main.utils.DateHelper;
import main.views.textfield.ChoiceBoxCostumized;

public class FiltrarDoacaoController implements InicializadorComAcao {

    @FXML
    private Button cancelarFiltro;

    @FXML
    private DatePicker dataFinalDatePicker;

    @FXML
    private DatePicker dataInicialDatePicker;

    @FXML
    private Button filtrar;

    @FXML
    private ChoiceBox<String> tiposOrdenacaoChoiceBox;
    private ChoiceBoxCostumized ordenacaoChoiceBox;

    private Acao acao;
    private DoacaoServices doacaoServices;
    
    @Override
    public void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao, Object[] dados) {
       this.acao = acao;
       inicializa();
       setListeners(primmaryStage, blackShadow);
       dataFinalDatePicker.setValue(LocalDate.now());
       dataInicialDatePicker.setValue(LocalDate.now());
       if(doacaoServices.filtro != null) setFiltros();
    }
    
    void getFiltros(){
        doacaoServices.filtro = (doacaoServices.filtro == null) ? new FiltroDespesa() : doacaoServices.filtro;
        doacaoServices.filtro.setDataFinal(DateHelper.LocalDateParaCalendar(dataFinalDatePicker.getValue()));
        doacaoServices.filtro.setDataInicial(DateHelper.LocalDateParaCalendar(dataInicialDatePicker.getValue()));
        doacaoServices.filtro.setOrdenacao(Mapping.GetKeyOrdenacoesDespesaDoacoes(ordenacaoChoiceBox.getValue()));      
    }
    
    private void inicializa(){
        doacaoServices = new DoacaoServices();
        tiposOrdenacaoChoiceBox.setItems(Mapping.getOrdenacoesDespesaDoacoes());
        ordenacaoChoiceBox = new ChoiceBoxCostumized();
        ordenacaoChoiceBox.initialize(tiposOrdenacaoChoiceBox);
    }
    
    private List<Doacao> FiltrarDoacoes()
    {
        getFiltros();
        
        if((doacaoServices.filtro.getDataInicial() == null) !=  (doacaoServices.filtro.getDataFinal() == null)){
           App.getInstance().SetMensagem(MensagemTipo.ERRO, "Voce deve selecionar um intervalo de datas", null);
           return null;
        }
        
        if(doacaoServices.filtro.getDataFinal()!= null && doacaoServices.filtro.getDataFinal().before(doacaoServices.filtro.getDataInicial())){
           App.getInstance().SetMensagem(MensagemTipo.ERRO, "A data final deve ser maior que a inicial", null);
           return null;
        }
        return doacaoServices.FiltrarDoacoes(doacaoServices.filtro);    
    }
    
    private void setFiltros(){
        if(doacaoServices.filtro.getDataFinal() != null)
            dataFinalDatePicker.setValue(DateHelper.CalendarParaLocalDate(doacaoServices.filtro.getDataFinal()));
        
        if(doacaoServices.filtro.getDataInicial() != null)
            dataInicialDatePicker.setValue(DateHelper.CalendarParaLocalDate(doacaoServices.filtro.getDataInicial()));
        
        tiposOrdenacaoChoiceBox.setValue(Mapping.GetKeyByValue(Mapping.getOrdenacoesDespesaDoacoesHash(), doacaoServices.filtro.getOrdenacao()));       
    }
    
    private void setListeners(Stage primmaryStage, Pane blackShadow){
        filtrar.setOnMouseClicked(e ->{
            var doacoes = FiltrarDoacoes();
            if(doacoes != null){
                 acao.RealizarAcao(new Object[]{ doacoes, doacaoServices.filtro });
                 App.getInstance().FecharDialog(primmaryStage, blackShadow);
            }       
        });      
        
        cancelarFiltro.setOnMouseClicked(e ->{
           App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });
    }  
}
