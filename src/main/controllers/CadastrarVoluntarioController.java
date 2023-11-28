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
import main.enums.MensagemTipo;
import main.interfaces.InicializadorComDado;
import main.model.Voluntario;
import main.services.VoluntarioService;
import static main.utils.Constantes.DIALOG_CADASTRAR_VOLUNTARIO;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.FORM_EQUIPE;
import static main.utils.Constantes.FORM_HOME;
import main.utils.ImageLoader;
import main.utils.Rectangles;
import static main.utils.Rectangles.GetCircleVoluntario;
import static main.utils.Rectangles.GetRectangleVoluntario;
import main.utils.ToogleEnum;
import main.utils.ValidacaoUtils;

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
    private Button salvarVoluntario, removerButton;

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
        ValidacaoUtils.mascaraEmail(emailVoluntario);
    }
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        salvarVoluntario.setOnMouseClicked(e ->{
            if(Cadastrar() != null)
                App.getInstance().EntrarTela(FORM_EQUIPE, contentFather, primmaryStage, blackShadow);  
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });
        
        layoutImageViewVoluntario.setOnMouseClicked(e -> {
            fotoVoluntario = CarregarImagem(primmaryStage, imagemVoluntario, layoutImageViewVoluntario, GetCircleVoluntario());
        });
        
        removerButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComAcao(DIALOG_REMOVER, DIALOG_CADASTRAR_VOLUNTARIO, contentFather, primmaryStage, blackShadow,new Object[]{"Deseja realmente deletar esse voluntário?"}, (dado) ->{
                if(voluntarioService.DeletarVoluntarioPorId(voluntario.getId()) == 1);
                    App.getInstance().EntrarTelaOnResume(FORM_EQUIPE, contentFather, primmaryStage, blackShadow, null);
            });
        });    
    }
    
    public Voluntario Cadastrar(){
        String nome = nomeVoluntario.getText();
        String email = emailVoluntario.getText();
        String telefone = telefoneVoluntario.getText();
        
        if(!validarVoluntario(nome, email)) return null;
        
        return voluntarioService.Salvar(idVoluntario ,nome, email, telefone, fotoVoluntario);
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
    
    public boolean validarVoluntario(String nome, String email){
        boolean nomeValido = ValidacaoUtils.validarCampo(nome, nomeVoluntario, "O nome não deve ser vazio");
        boolean emailValido = ValidacaoUtils.validarCampo(email, emailVoluntario, "O email não deve ser vazio");
        boolean emailFormatado = ValidacaoUtils.isValidEmailAddress(email);
        if(!emailFormatado){
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Email inválido");
            ValidacaoUtils.exibirErro(emailVoluntario, "Email inválido");
        }else{
            ValidacaoUtils.limparErro(emailVoluntario);
        }
        return nomeValido && emailValido && emailFormatado;
    }

}