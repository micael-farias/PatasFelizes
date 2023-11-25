package main.controllers;

import com.sun.javafx.css.StyleManager;
import java.util.Calendar;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.css.Rule;
import javafx.css.StyleOrigin;
import main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.factories.StatusAnimalFactory;
import main.interfaces.InicializadorComDado;
import main.interfaces.Resumidor;
import main.model.Animal;
import main.model.Idade;
import main.model.Procedimento;
import main.model.Voluntario;
import main.services.AnimalService;
import main.services.ProcedimentoService;
import static main.utils.Constantes.DIALOG_CADASTRAR_ADOCAO;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.FORM_HOME;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateHelper.CalculaAnosEMesesPorDt;
import main.utils.ImageLoader;
import main.utils.NumberHelper;
import main.utils.Rectangles;
import main.utils.ToogleEnum;
import static main.utils.ToogleEnum.DIREITO;
import static main.utils.ToogleEnum.ESQUERDO;
import main.views.gridview.ProcedimentoGridView;
import main.views.toggle.ToggleView;

public class AnimalDetalhesController  extends AnimalFormularioController implements InicializadorComDado, Resumidor{
    
    @FXML
    private ImageView adotarButton;

    @FXML
    private TextField anosAnimalTextField;

    @FXML
    private TextArea descricaoAnimalTextField;

    @FXML
    private ImageView imagemAnimal;

    @FXML
    private VBox layoutImageViewAnimal;

    @FXML
    private TextField mesesAnimalTextField;

    @FXML
    private TextField nomeAnimalTextField;

    @FXML
    private GridPane procedimentosGridView;

    @FXML
    private Button removerButton;
    @FXML
    private Button cancelarCadastro;

    @FXML
    private Button salvarAnimal;

    @FXML
    private MenuButton statusAnimal;

    @FXML
    private HBox toggleSexo;

    @FXML
    private HBox toogleCastrado;

    @FXML
    private ImageView voltarButton;

    @FXML
    private ImageView sexoDesconhecidoCheckBox;
    
    @FXML
    private TextField textFieldBuscarProcedimento;
    
    @FXML
    private StackPane stackPaneScroll;
    
    private byte[] fotoAnimal;
    private String ultimoStatus;
    private char sexoAnimalValor;
    ToggleView toggleViewSexo;
    ToggleView toogleViewCastrado;
    ProcedimentoService procedimentoService;
    AnimalService animalService;
    
    private static Animal ultimoAnimal;
    @FXML
    private Label labelNome;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dado) {
        initialize(dado);
        initializeViews(contentFather,primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(Object[] dado){

   
    ultimoAnimal = (dado != null) ? (Animal) ObterDadoArray(dado, 0) : ultimoAnimal;
        animalService =  new AnimalService();
        procedimentoService = new ProcedimentoService();
        configuraToggles();
        setData(ultimoAnimal);  
    }
    
    public void initializeViews(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        criarGridProcedimentos(contentFather,primmaryStage, blackShadow);
    }
    
    public void criarGridProcedimentos(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        List<Procedimento> procedimentos = procedimentoService.EncontrarProcedimentosPor(ultimoAnimal.getId());
        ProcedimentoGridView procedimentosGrid = new ProcedimentoGridView(contentFather, primmaryStage,stackPaneScroll, blackShadow, procedimentosGridView, 1, procedimentos, ultimoAnimal.getId());
        procedimentosGrid.createGridAsync();   
    }
    
    public void configuraToggles(){
        toggleViewSexo = configuraToggleSexo(toggleSexo);
        toogleViewCastrado = configuraToggleCastrado(toogleCastrado);
    }
    
    public void setData(Animal animal){
        ImageLoader.CarregarImagem(imagemAnimal, animal.getFoto(), animal.idFoto(), Rectangles.GetRectangleImageAnimaisDetails());
        nomeAnimalTextField.setText(animal.getNome());
              
        Idade idadeAnimal = CalculaAnosEMesesPorDt(animal.getDataNascimento());
        sexoAnimalValor = animal.getSexo();
        boolean animalSemSexoDefinido = sexoAnimalValor == 'N';
        ultimoStatus = animal.getStatus();
        anosAnimalTextField.setText(String.valueOf(idadeAnimal.getAnos()));
        mesesAnimalTextField.setText(String.valueOf(idadeAnimal.getMeses()));
        statusAnimal.setText(StatusAnimalFactory.GetStatusString(animal.getStatus()));
        descricaoAnimalTextField.setText(animal.getDescricao());
        setImage(animalSemSexoDefinido);
        if(animalSemSexoDefinido){
            toggleViewSexo.desativarToogle();
        }else{
            toggleViewSexo.ativarBotao((animal.getSexo() == 'M') ? DIREITO : ESQUERDO);
        }
        
        toogleViewCastrado.ativarBotao((animal.isCastrado()) ? DIREITO : ESQUERDO);
    }
    
    public void atualizarAnimal(Stage primaryStage) {
        String nomeAnimal = nomeAnimalTextField.getText();
        String anosAnimal = anosAnimalTextField.getText();
        String mesesAnimal = mesesAnimalTextField.getText() == null ? "0" : mesesAnimalTextField.getText();
        String descricaoAnimal = descricaoAnimalTextField.getText();
        ToogleEnum sexoAnimal;
        if(sexoAnimalValor == 'N'){
            sexoAnimal = null;
        }else{
            sexoAnimal = toggleViewSexo.getSelectedItem();   
        } 
                                
        ToogleEnum castrado = toogleViewCastrado.getSelectedItem();

        animalService.Salvar(ultimoAnimal.getId() ,nomeAnimal, anosAnimal, mesesAnimal, descricaoAnimal, sexoAnimal, castrado, fotoAnimal, ultimoStatus);
    }
    
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow) {
        salvarAnimal.setOnMouseClicked(e -> {
            atualizarAnimal(primaryStage);
            App.getInstance().EntrarTelaComRemocao(FORM_HOME, FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow);
        });

        layoutImageViewAnimal.setOnMouseClicked(e -> {
            fotoAnimal = CarregarImagem(primaryStage, imagemAnimal, layoutImageViewAnimal, Rectangles.GetRectangleImageAnimaisDetails());
        });

        statusAnimal.getItems().forEach(item -> item.setOnAction(event -> {
            statusAnimal.setText(item.getText());
            ultimoStatus = item.getText();
        }));
        
        voltarButton.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaNoActionComRemocao(FORM_HOME, FORM_ANIMAL_DETALHES,contentFather, primaryStage, blackShadow);
        });
        
        voltarButton.setOnMouseEntered(e ->{
            voltarButton.setImage(new Image(PATH_IMAGES + "voltar_claro.png"));
        });
        
        voltarButton.setOnMouseExited(e ->{
            voltarButton.setImage(new Image(PATH_IMAGES + "voltar_escuro.png"));
        });
                
        adotarButton.setOnMouseEntered(e ->{
            adotarButton.setImage(new Image(PATH_IMAGES + "adotar_claro.png"));
        });
        
        adotarButton.setOnMouseExited(e ->{
            adotarButton.setImage(new Image(PATH_IMAGES + "adotar_escuro.png"));
        });
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaNoActionComRemocao(FORM_HOME, FORM_ANIMAL_DETALHES,contentFather, primaryStage, blackShadow);
        });
        
        textFieldBuscarProcedimento.setOnKeyPressed(e ->{
            String nome = textFieldBuscarProcedimento.getText();
            if(e.getCode().equals(ENTER)){
                  List<Procedimento> procedimentos = procedimentoService.EncontrarProcedimentosPor(nome, ultimoAnimal.getId());
                  ProcedimentoGridView procedimentosGrid = new ProcedimentoGridView(contentFather, primaryStage,stackPaneScroll, blackShadow, procedimentosGridView, 1, procedimentos, ultimoAnimal.getId());
                  procedimentosGrid.createGridAsync();
            }
        });  
                
        adotarButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_ADOCAO, contentFather, primaryStage, blackShadow);
        });
        
        removerButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComOrigemEDado(DIALOG_REMOVER, FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow, new Object[]{ultimoAnimal.getId()});
        });       
        
        sexoDesconhecidoCheckBox.setOnMouseClicked(event -> {
            if (sexoAnimalValor == 'N') {
                sexoAnimalValor = toggleViewSexo.getSelectedItem() == ToogleEnum.DIREITO ? 'F' : 'M';
                toggleViewSexo.ativarToogle();
            }else{
                sexoAnimalValor = 'N';
                toggleViewSexo.desativarToogle();
            }
             
            setImage(sexoAnimalValor == 'N');
       });
    }
    
    public void setImage(boolean animalSemSexo){
        if(animalSemSexo){
                sexoDesconhecidoCheckBox.setImage(new Image(PATH_IMAGES + "check_azul_checked.png"));       
        }else{
                sexoDesconhecidoCheckBox.setImage(new Image(PATH_IMAGES + "check_azul_not_checked.png"));        
        }
    }
    
    
    public void handleKeyTypedAnos(KeyEvent event) {
        char input = event.getCharacter().charAt(0);

        if (!Character.isDigit(input) || anosAnimalTextField.getText().length() >= 2) {
              manterTexto(anosAnimalTextField);     
        } else {
            String currentText = anosAnimalTextField.getText();
            int ano = NumberHelper.IntegerParse(currentText);
            if(ano == 1 || ano == 2){
                anosAnimalTextField.setTextFormatter(criarTextFormatter(2));
            }else{
                anosAnimalTextField.setTextFormatter(criarTextFormatter(1));
            }
        }
    }

    @FXML
    public void handleKeyTypedMeses(KeyEvent event) {
        char input = event.getCharacter().charAt(0);
        if (!Character.isDigit(input)) {
              manterTexto(mesesAnimalTextField);     
        } else {
            String currentText = mesesAnimalTextField.getText();
            int ano = NumberHelper.IntegerParse(currentText);
            if(ano == 1 || ano == 0){
                mesesAnimalTextField.setTextFormatter(criarTextFormatter(2));
                manterTexto(mesesAnimalTextField);     
            }else if(ano > 12){
                mesesAnimalTextField.setTextFormatter(criarTextFormatter(1));
                removerUltimoDigito(mesesAnimalTextField);     
            }else{
                anosAnimalTextField.positionCaret(currentText.length());   
                mesesAnimalTextField.setTextFormatter(criarTextFormatter(1));
            }
        }
    }

    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        ultimoAnimal = (dados != null) ? (Animal) ObterDadoArray(dados, 0) : ultimoAnimal;
        criarGridProcedimentos(contentFather, primmaryStage, blackShadow);
    }
}
