/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import main.interfaces.InicializadorComOrigemEDado;
import main.model.Animal;
import main.model.Procedimento;
import main.services.ProcedimentoService;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import main.utils.ToogleEnum;
import main.views.textfield.AutoCompleteTextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
/**
 *
 * @author micha
 */
public class CadastrarProcedimentoController extends CustomController implements InicializadorComOrigemEDado{
    @FXML
    private DatePicker dataProcedimento;

    @FXML
    private TextField descricaoProcedimento;

    @FXML
    private Button salvarProcedimento;

    @FXML
    private TextField tipoProcedimento;
    
    @FXML
    private TextField valorProcedimento;

    @FXML
    private TextField voluntarioProcedimento;
    
    private ProcedimentoService procedimentoService;
    private int idAnimal;
    private Procedimento procedimento;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, String origem, Object[] dados) {
        idAnimal = ObterDadoArray(dados, 0) == null ? -1 : (int) ObterDadoArray(dados, 0) ;
        procedimento = ObterDadoArray(dados,1) == null ? null : (Procedimento) ObterDadoArray(dados, 1);
        
        
        initialize();
        
       configurarPets();
        setListeners(origem, contentFather, primmaryStage, blackShadow);     
    }
      public void configurarPets(){
        Set<String> animaisPossiveis =new HashSet<>();
        animaisPossiveis.add("sdfhjsdds");
        animaisPossiveis.add("sjkxoajxazlk");
        TextFields.bindAutoCompletion(tipoProcedimento, animaisPossiveis);   
        tipoProcedimento.setOnKeyPressed(e -> {
            if(e.getCode() == ENTER){
                //autoCompletionLearnWord(tipoProcedimento.getText().toString());
            }
        });
    }  
   
  
    public void initialize(){
        procedimentoService = new ProcedimentoService();
    }
    
    public void setListeners(String origem, Pane contentFather, Stage primmaryStage, Pane blackShadow){
        salvarProcedimento.setOnMouseClicked(e->{
            Procedimento procedimento = cadastrarNovoProcedimento(primmaryStage);
            App.getInstance().EntrarTelaOnResume(origem ,contentFather, primmaryStage, blackShadow, new Object[]{procedimento});                       
        });
    }
    
    public Procedimento cadastrarNovoProcedimento(Stage primaryStage) {
        String descricao = descricaoProcedimento.getText();
        LocalDate data = dataProcedimento.getValue();
        String tipo = tipoProcedimento.getText();
        String voluntario = voluntarioProcedimento.getText();
        String valor = valorProcedimento.getText();

        return procedimentoService.Salvar(procedimento == null ? 0 : procedimento.getId(), descricao, data, tipo, valor, voluntario, idAnimal);      
    }

}
