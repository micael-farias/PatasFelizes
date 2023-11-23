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
    
    private Tarefa tarefa;
    private TarefaServices tarefasService; 
    private VoluntarioService voluntarioService;
    private AnimalService animalServices;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        
        tarefa = ObterDadoArray(dados,0) == null ? null : (Tarefa) ObterDadoArray(dados, 0);
        
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
        
        configurarPets();
        configurarVoluntarios();
        configurarTiposTarefa();
        
        if(tarefa != null) setData();
    }

    private void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        salvarTarefa.setOnMouseClicked(e->{
            cadastrarNovaTarefa(primmaryStage);
            App.getInstance().EntrarTelaOnResume(FORM_TAREFAS ,contentFather, primmaryStage, blackShadow, null);                       
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().FecharDialog(primmaryStage, blackShadow);                       
        });
    }

    public Tarefa cadastrarNovaTarefa(Stage primaryStage) {
        String descricao = descricaoTarefa.getText();
        LocalDate data = dataTarefa.getValue();
        String tipo = tipoTarefa.getText();
        String voluntario = responsavelTarefa.getText();
        String animal = petTarefa.getText();
        

        return tarefasService.Salvar(tarefa == null ? -1 : tarefa.getId(), voluntario, animal, descricao, data, tipo, null);      
    }

    private void setData() {
        descricaoTarefa.setText(tarefa.getDescricao());
        LocalDate localDate = tarefa.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dataTarefa.setValue(localDate);
        tipoTarefa.setText(tarefa.getTipo());
        responsavelTarefa.setText(tarefa.getVoluntario() != null ? tarefa.getVoluntario().getNome() : "");
        petTarefa.setText(tarefa.getAnimal() != null ? tarefa.getAnimal().getNome() : "");
    }
}
