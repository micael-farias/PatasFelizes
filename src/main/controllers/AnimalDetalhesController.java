package main.controllers;

import java.util.List;
import main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.enums.MensagemTipo;
import main.factories.StatusAnimalFactory;
import main.interfaces.InicializadorComDado;
import main.interfaces.Resumidor;
import main.model.Adocao;
import main.model.Animal;
import main.model.Idade;
import main.model.Procedimento;
import main.services.AdocaoServices;
import main.services.AnimalService;
import main.services.ProcedimentoService;
import static main.utils.Constantes.DIALOG_CADASTRAR_ADOCAO;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.FORM_HOME;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalculaAnosEMesesPorDt;
import static main.utils.DateHelper.invalidString;
import main.utils.ImageLoader;
import main.utils.NumberHelper;
import main.utils.Rectangles;
import main.utils.TextFieldUtils;
import static main.utils.TextFieldUtils.autoCapitalizeFirstLetter;
import static main.utils.TextFieldUtils.capitalizeEachWord;
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
    private Button cancelarCadastro;

    @FXML
    private Label dataAdocao;

    @FXML
    private TextArea descricaoAnimalTextField;

    @FXML
    private ImageView imagemAnimal;

    @FXML
    private VBox layoutAdicionarAdocao;

    @FXML
    private Pane layoutAdocao;

    @FXML
    private VBox layoutImageViewAnimal;

    @FXML
    private AnchorPane layoutRemoverAdocao;

    @FXML
    private TextField mesesAnimalTextField;

    @FXML
    private Label nomeAdotante;

    @FXML
    private TextField nomeAnimalTextField;

    @FXML
    private GridPane procedimentosGridView;

    @FXML
    private Button removerButton;

    @FXML
    private Button salvarAnimal;

    @FXML
    private ImageView sexoDesconhecidoCheckBox;

    @FXML
    private StackPane stackPaneScroll;

    @FXML
    private MenuButton statusAnimal;

    @FXML
    private TextField textFieldBuscarProcedimento;

    @FXML
    private HBox toggleSexo;

    @FXML
    private HBox toogleCastrado;
    
    @FXML
    private HBox boxNomeAdotante;
    @FXML
    private ImageView voltarButton;    
    
    private byte[] fotoAnimal;
    private String ultimoStatus;
    private char sexoAnimalValor;
    private ToggleView toggleViewSexo;
    private ToggleView toogleViewCastrado;
    private ProcedimentoService procedimentoService;
    private AnimalService animalService;
    private AdocaoServices adocaoService;
    
    private static Adocao adocao;
    private static Animal ultimoAnimal;


    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dado) {
        initialize(dado);
        initializeViews(contentFather,primmaryStage, blackShadow);
        setListeners(contentFather, primmaryStage, blackShadow);
        configurarLayoutAdocao();
    }
    
    public void initialize(Object[] dado){

        
        ultimoAnimal = (dado != null) ? (Animal) ObterDadoArray(dado, 0) : ultimoAnimal;
        animalService =  new AnimalService();
        procedimentoService = new ProcedimentoService();
        adocaoService  = new AdocaoServices();
        configuraToggles();
        setData(ultimoAnimal);  
        textFormatter(mesesAnimalTextField, anosAnimalTextField);
        autoCapitalizeFirstLetter(descricaoAnimalTextField);
        capitalizeEachWord(nomeAnimalTextField);
        autoCapitalizeFirstLetter(textFieldBuscarProcedimento);

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
        if(idadeAnimal != null){
            anosAnimalTextField.setText(String.valueOf(idadeAnimal.getAnos()));
            mesesAnimalTextField.setText(String.valueOf(idadeAnimal.getMeses()));
        }

        statusAnimal.setText(StatusAnimalFactory.GetStatusString(animal.getStatus()));
        descricaoAnimalTextField.setText(animal.getDescricao());
        setImage(animalSemSexoDefinido);
        if(animalSemSexoDefinido){
            toggleViewSexo.desativarToogle();
        }else{
            toggleViewSexo.ativarBotao((animal.getSexo() == 'F') ? DIREITO : ESQUERDO);
        }
        
        toogleViewCastrado.ativarBotao((animal.isCastrado()) ? DIREITO : ESQUERDO);
    }
    
    public Animal atualizarAnimal(Stage primaryStage) {
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
        
        if(ultimoStatus.equals("Adotado") && adocao == null){
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Não foi encontrada uma adoção para o animal");
            return null;
        }
                if(!validarPet(nomeAnimal)) return null;

        Animal animal = animalService.Salvar(ultimoAnimal.getId() ,nomeAnimal, anosAnimal, mesesAnimal, descricaoAnimal, sexoAnimal, castrado, fotoAnimal, ultimoStatus);
        if(animal.getStatus().equals("PA") && adocao != null) adocaoService.DeletarAdocaoPorId(adocao.getId(), ultimoAnimal.getId());
        return animal;
    }
    
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow) {
        salvarAnimal.setOnMouseClicked(e -> {
            if(atualizarAnimal(primaryStage) != null)
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
            App.getInstance().EntrarTelaComRemocao(FORM_HOME, FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow);
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
        
        boxNomeAdotante.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComAcao(DIALOG_CADASTRAR_ADOCAO,FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow,
                    new Object[]{ultimoAnimal.getId(), adocao}, (dado) -> { configurarLayoutAdocao();} );
        });
        
        
        textFieldBuscarProcedimento.setOnKeyPressed(e ->{
            String nome = textFieldBuscarProcedimento.getText();
            if(e.getCode().equals(ENTER)){
                  List<Procedimento> procedimentos = procedimentoService.EncontrarProcedimentosPor(nome, ultimoAnimal.getId());
                  ProcedimentoGridView procedimentosGrid = new ProcedimentoGridView(contentFather, primaryStage,stackPaneScroll, blackShadow, procedimentosGridView, 1, procedimentos, ultimoAnimal.getId());
                  procedimentosGrid.createGridAsync();
            }
        });  
                
        layoutRemoverAdocao.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComAcao(DIALOG_REMOVER, FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow,new Object[]{"Deseja realmente deletar essa adoção"}, (dado) ->{
                adocaoService.DeletarAdocaoPorId(adocao.getId(), ultimoAnimal.getId());
                configurarLayoutAdocao(); 
                statusAnimal.setText("Para adoção");
            });
        
        });
        
        
        
        adotarButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComAcao(DIALOG_CADASTRAR_ADOCAO,FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow, new Object[]{ultimoAnimal.getId(), adocao}, (dado) -> {           
                configurarLayoutAdocao();
                statusAnimal.setText("Adotado");
            } );
        });
        
        removerButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComAcao(DIALOG_REMOVER, FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow,new Object[]{"Deseja realmente deletar esse animal? Tudo ao seu respeito será removido."}, (dado) ->{
                if(animalService.DeletarAnimalPorId(ultimoAnimal.getId()));
                    App.getInstance().EntrarTelaOnResume(FORM_HOME, contentFather, primaryStage, blackShadow, null);
            });
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
    
    public void configurarLayoutAdocao(){
        adocao = adocaoService.EncontrarAdocaoPorId(ultimoAnimal.getId());
        if(adocao != null){
            layoutAdicionarAdocao.setVisible(false);
            layoutAdocao.setVisible(true);
            nomeAdotante.setText(adocao.getAdotante().getNome());
            dataAdocao.setText(DateHelper.CalendarParaString(adocao.getDataCadastro()));
            layoutRemoverAdocao.setVisible(true);
        }else{
            layoutAdocao.setVisible(false);
            layoutRemoverAdocao.setVisible(false);
            layoutAdicionarAdocao.setVisible(true);       
        }
    }
     public boolean validarPet(String nome){
        if (invalidString(nome)) {
            nomeAnimalTextField.setPromptText("O nome do animal não deve ser vazio");
            nomeAnimalTextField.setStyle(nomeAnimalTextField.getStyle() + "-fx-border-color: red ; -fx-border-width: 1px ;");
            return false;
        } else {
            nomeAnimalTextField.setPromptText("");
            nomeAnimalTextField.setStyle(nomeAnimalTextField.getStyle() + "-fx-border-color: black ; -fx-border-width: 1px ;");
        }

        
        return true;
    }



    @Override
    public void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        ultimoAnimal = (dados != null) ? (Animal) ObterDadoArray(dados, 0) : ultimoAnimal;
        criarGridProcedimentos(contentFather, primmaryStage, blackShadow);
        configurarLayoutAdocao();

    }
}
