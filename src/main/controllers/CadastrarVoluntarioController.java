package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorComDado;
import main.services.VoluntarioService;
import static main.utils.Constantes.FORM_EQUIPE;
import static main.utils.Constantes.FORM_FINANCAS;

public class CadastrarVoluntarioController extends CustomController implements InicializadorComDado{

    @FXML
    private TextField emailVoluntario;

    @FXML
    private ImageView imagemVoluntario;

    @FXML
    private VBox layoutImageViewVoluntario;

    @FXML
    private TextField nomeVoluntario;

    @FXML
    private Button salvarVoluntario;

    @FXML
    private TextField telefoneVoluntario; 
    
    @FXML
    private Button cancelarCadastro;
    
    private VoluntarioService voluntarioService;
    
    private int idVoluntario;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado) {
         idVoluntario = (int) dado;

    }
    
    public void initialise(){
        voluntarioService = new VoluntarioService();
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        salvarVoluntario.setOnMouseClicked(e ->{
            Cadastrar();
            App.getInstance().EntrarTela(FORM_EQUIPE, contentFather, primmaryStage, blackShadow);  
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaOnResume(FORM_EQUIPE, contentFather, primmaryStage, blackShadow, null);
        });
    }
    
    public void Cadastrar(){
        String nome = nomeVoluntario.getText();
        String email = emailVoluntario.getText();
        String telefone = telefoneVoluntario.getText();
        
        voluntarioService.Salvar(idVoluntario ,nome, email, telefone);
    }
    
    

}