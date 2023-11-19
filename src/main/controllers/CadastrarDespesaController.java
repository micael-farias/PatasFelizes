package main.controllers;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorComDado;
import main.services.AnimalService;
import main.services.DespesaServices;
import static main.utils.Constantes.FORM_FINANCAS;
import static main.utils.Constantes.FORM_HOME;
import org.controlsfx.control.textfield.TextFields;

public class CadastrarDespesaController implements InicializadorComDado{

    @FXML
    private DatePicker dataDespesa;

    @FXML
    private TextField descricaoDespesa;

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

    DespesaServices despesaServices;
    AnimalService animalServices;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado) {
        initialize();
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        animalServices = new AnimalService();
        despesaServices = new DespesaServices();
        
        configurarPets();
        configurarTiposDespesa();
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
            App.getInstance().EntrarTelaOnResume(FORM_FINANCAS ,contentFather, primmaryStage, blackShadow, null);                      
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaOnResume(FORM_FINANCAS, contentFather, primmaryStage, blackShadow, null);
        });        
    }
    
    public void SalvarDespesa(){
        LocalDate data = dataDespesa.getValue();
        String descriao = descricaoDespesa.getText();
        String pet = petDespesa.getText();
        String tipo = tipoDespesa.getText();
        String valor = valorDespesa.getText();
        
        despesaServices.Cadastrar(descriao, valor, data, pet, tipo);
    }
}
