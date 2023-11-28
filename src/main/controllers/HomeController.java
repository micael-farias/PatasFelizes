package main.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.enums.Mapping;
import main.enums.MensagemTipo;
import static main.enums.MensagemTipo.ERRO;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Animal;
import main.model.FiltroDespesa;
import main.model.FiltrosAnimais;
import main.model.Procedimento;
import main.repositories.AnimalRepository;
import main.services.AnimalService;
import static main.utils.Constantes.DIALOG_FILTRAR_ANIMAL;
import main.views.gridview.AnimalGridView;
import static main.utils.Constantes.DIALOG_MENSAGEM;
import static main.utils.Constantes.FORM_HOME;
import main.utils.DateHelper;

public class HomeController implements Inicializador, Resumidor{    

    @FXML
    private TextField buscarAnimalTextField;
    
    @FXML
    private StackPane stackPaneScroll;
    
    @FXML
    private Button filtrarAnimaisButton;
    
    @FXML
    private GridPane animaisGrid;
    
    private AnimalRepository repository;
    private AnimalService animalService;
 
    private static FiltrosAnimais filtro;
    
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize();
        criarGridAnimais(contentFather, primmaryStage, blackShadow);
        setListeners(contentFather, blackShadow, primmaryStage);
    }
    
    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados){
        hintBuscarAnimais();
        criarGridAnimais(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        this.repository = new AnimalRepository();
        this.animalService= new AnimalService();
        hintBuscarAnimais();
    }
    
    public void hintBuscarAnimais(){
        buscarAnimalTextField.setPromptText("Procurar animal por nome");
    }
    
    public void setListeners(Pane contentFather, Pane blackShadow, Stage primmaryStage){
        filtrarAnimaisButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComAcao(DIALOG_FILTRAR_ANIMAL, FORM_HOME, contentFather, primmaryStage, blackShadow, null, (dados) ->{
                List<Animal> animais = (List<Animal>)dados[0];
                filtro = (FiltrosAnimais) dados[1];
                criarGridComResultados(animais,contentFather, primmaryStage, blackShadow);
            });
        });
        
        buscarAnimalTextField.setOnKeyPressed(e ->{
            String animal = buscarAnimalTextField.getText();
            if(e.getCode().equals(ENTER)){
                  List<Animal> animais = repository.EncontrarAnimaisPorNome(animal);
                  criarGridComResultados(animais, contentFather, primmaryStage, blackShadow);
            }else if(e.getCode().equals(BACK_SPACE)){
                if(animal.length() == 0) hintBuscarAnimais();
            }
        });
        
        
    }
    
    public void criarGridAnimais(Pane contentFather, Stage primmaryStage, Pane blackShadow){    
        if(filtro != null){
            
            Calendar intervaloUm = DateHelper.ConvertMesAnoToCalendar(filtro.getIntervaloPrimeiroAno(),
                filtro.getIntervaloPrimeiroMeses());
            Calendar intervaloDois = DateHelper.ConvertMesAnoToCalendar(filtro.getIntervaloSegundoAno(),
                filtro.getIntervaloSegundoMeses());
            
            var animais =  animalService.selecionarAnimais(Mapping.GetKeyOrdenacoes(filtro.getOrdenacaoSelecionada()),
                    Mapping.GetKeyStatus(filtro.getStatusSelecionado()), filtro.isFiltrarMasculino(), filtro.isFiltrarFeminino(), 
                    filtro.isFiltrarCastradoSim(), filtro.isFiltrarCastradoNao(), intervaloUm, intervaloDois);
                            
            if(animais != null) criarGridComResultados(animais, contentFather, primmaryStage, blackShadow);   
        }else{
            List<Animal> animais = repository.EncontrarAnimais();
            criarGridComResultados(animais, contentFather, primmaryStage, blackShadow);
        }
    }
    
    public void criarGridComResultados(List<Animal> itens, Pane contentFather, Stage primmaryStage, Pane blackShadow){
        AnimalGridView animalGridView = new AnimalGridView(animaisGrid, 4, itens, contentFather, primmaryStage, blackShadow, stackPaneScroll);
        animalGridView.createGridAsync();    
    }
}
