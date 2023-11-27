/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import java.util.Calendar;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import main.model.Procedimento;
import main.model.Tarefa;
import main.services.ProcedimentoService;
import main.services.TarefaServices;
import main.services.VoluntarioService;
import org.controlsfx.control.textfield.TextFields;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.model.Animal;
import main.services.AnimalService;
import static main.utils.Constantes.FORM_TAREFAS;
/**
 *
 * @author grazi
 */
public class CadastrarTarefaController extends CustomController implements InicializadorComDado {


    @FXML
    private Button cancelarCadastro;

    @FXML
    private DatePicker dataTarefa;

    @FXML
    private TextArea descricaoTarefa;

    @FXML
    private TextField petTarefa;

    @FXML
    private TextField responsavelTarefa;

    @FXML
    private Button salvarTarefa;

    @FXML
    private TextField tipoTarefa;
    
    private Procedimento procedimento;
    private TarefaServices tarefasService; 
    private VoluntarioService voluntarioService;
    private ProcedimentoService procedimentoService;
    private AnimalService animalServices;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        
        procedimento = ObterDadoArray(dados,0) == null ? null : (Procedimento) ObterDadoArray(dados, 0);
        
        initialize();
        setListeners(contentFather, primmaryStage, blackShadow);     
    }
    
    public void configurarPets(){
       Set<String> voluntariosPossiveis = animalServices.ObterNomesAnimais();
       TextFields.bindAutoCompletion(petTarefa, voluntariosPossiveis);   
    }
    
    
    public void configurarVoluntarios(){
       Set<String> voluntariosPossiveis = voluntarioService.ObterNomeVoluntarios();
       TextFields.bindAutoCompletion(responsavelTarefa, voluntariosPossiveis);   
    }
    
    public void configurarTiposTarefa(){
       Set<String> tiposPossiveis = tarefasService.ObterNomesTiposTarefa();
       TextFields.bindAutoCompletion(tipoTarefa, tiposPossiveis);   
    }
     
    public void initialize(){
        tarefasService = new TarefaServices();
        voluntarioService = new VoluntarioService();
        animalServices = new AnimalService();
        procedimentoService= new ProcedimentoService();
        
        configurarPets();
        configurarVoluntarios();
        configurarTiposTarefa();
        
        if(procedimento != null) setData();
    }

    private void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        salvarTarefa.setOnMouseClicked(e->{
            if(cadastrarNovaTarefa(primmaryStage) != null)
                App.getInstance().EntrarTelaOnResume(FORM_TAREFAS ,contentFather, primmaryStage, blackShadow, null);                       
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().FecharDialog(primmaryStage, blackShadow);                       
        });
    }

    public Procedimento cadastrarNovaTarefa(Stage primaryStage) {
        String descricao = descricaoTarefa.getText();
        LocalDate data = dataTarefa.getValue();
        String tipo = tipoTarefa.getText();
        String voluntario = responsavelTarefa.getText();
        String animalString = petTarefa.getText();
        Animal animal = animalServices.ObterAnimalPorNome(animalString);

        return procedimentoService.Salvar(procedimento == null ? -1 : procedimento.getId(), descricao, data, tipo, 0.0, voluntario, animal.getId(), null);      
    }

    private void setData() {
        descricaoTarefa.setText(procedimento.getDescricao());
        LocalDate localDate = procedimento.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dataTarefa.setValue(localDate);
        tipoTarefa.setText(procedimento.getTipo());
        responsavelTarefa.setText(procedimento.getVoluntario() != null ? procedimento.getVoluntario().getNome() : "");
        petTarefa.setText(procedimento.getAnimal() != null ? procedimento.getAnimal().getNome() : "");
    }
}
