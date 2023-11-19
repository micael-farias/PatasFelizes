package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorComDado;
import static main.utils.Constantes.FORM_FINANCAS;

public class CadastrarDoacaoController implements InicializadorComDado{
    
    @FXML
    private TextField dataDoacao;

    @FXML
    private TextField descricaoDoacao;

    @FXML
    private Button salvarDoacao;

    @FXML
    private TextField tipoDoacao;

    @FXML
    private TextField valorDoacao;

    @FXML
    private TextField voluntárioDoacao;
    
    @FXML
    private Button cancelarCadastro;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado) {
        setListeners(contentFather, primmaryStage, blackShadow);
    }   
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaOnResume(FORM_FINANCAS, contentFather, primmaryStage, blackShadow, null);
        });
        
        salvarDoacao.setOnMouseClicked(e->{
            App.getInstance().EntrarTelaOnResume(FORM_FINANCAS ,contentFather, primmaryStage, blackShadow, null);                      
        });       
    }
}
