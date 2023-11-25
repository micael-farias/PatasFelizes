package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import static main.controllers.AnimalFormularioController.CarregarImagem;
import main.interfaces.InicializadorComDado;
import main.model.Voluntario;
import main.services.VoluntarioService;
import static main.utils.Constantes.FORM_EQUIPE;
import main.utils.ImageLoader;
import main.utils.Rectangles;
import static main.utils.Rectangles.GetCircleVoluntario;
import static main.utils.Rectangles.GetRectangleVoluntario;
import main.utils.ToogleEnum;

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
    private Voluntario voluntario;
    private byte[] fotoVoluntario;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado[]) {
         idVoluntario = ObterDadoArray(dado, 0) == null ? -1 : (int) ObterDadoArray(dado, 0);
         voluntario = ObterDadoArray(dado, 1) == null ? null : (Voluntario) ObterDadoArray(dado, 1);
         initialise(primmaryStage);
         setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialise(Stage primmaryStage){
        voluntarioService = new VoluntarioService();
        setData(primmaryStage);
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        salvarVoluntario.setOnMouseClicked(e ->{
            Cadastrar();
            App.getInstance().EntrarTela(FORM_EQUIPE, contentFather, primmaryStage, blackShadow);  
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });
        
        layoutImageViewVoluntario.setOnMouseClicked(e -> {
            fotoVoluntario = CarregarImagem(primmaryStage, imagemVoluntario, layoutImageViewVoluntario, GetCircleVoluntario());
        });
    }
    
    public void Cadastrar(){
        String nome = nomeVoluntario.getText();
        String email = emailVoluntario.getText();
        String telefone = telefoneVoluntario.getText();
        
        voluntarioService.Salvar(idVoluntario ,nome, email, telefone, fotoVoluntario);
    }
    
    public void setData(Stage primmaryStage){
        if(voluntario != null){
            nomeVoluntario.setText(voluntario.getNome());
            emailVoluntario.setText(voluntario.getEmail());
            telefoneVoluntario.setText(voluntario.getTelefone());
            fotoVoluntario = voluntario.getFoto();
            ImageLoader.CarregarImagem(imagemVoluntario, fotoVoluntario, voluntario.idFoto(), Rectangles.GetRectangleVoluntario());
            imagemVoluntario.setFitHeight(150.4);
            imagemVoluntario.setFitWidth(150.4);
        }
    }
    
    

}