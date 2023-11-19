package main.controllers;

import java.util.List;
import main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.interfaces.InicializadorComDado;
import main.interfaces.Resumidor;
import main.model.Animal;
import main.model.Idade;
import main.model.Procedimento;
import main.services.AnimalService;
import main.services.ProcedimentoService;
import static main.utils.Constantes.DIALOG_CADASTRAR_ADOCAO;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.DateHelper.CalculaAnosEMesesPorDt;
import main.utils.ImageLoader;
import main.utils.IntegerHelper;
import main.utils.Rectangles;
import main.utils.ToogleEnum;
import static main.utils.ToogleEnum.DIREITO;
import static main.utils.ToogleEnum.ESQUERDO;
import main.views.gridview.ProcedimentoGridView;
import main.views.toggle.ToggleView;

public class AnimalDetalhesController  extends AnimalFormularioController implements InicializadorComDado, Resumidor{
      
  @FXML
    private Button adicionarProcedimentoButton;

    @FXML
    private VBox adotarButton;

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
    private Button salvarAnimal;

    @FXML
    private MenuButton statusAnimal;

    @FXML
    private HBox toggleSexo;

    @FXML
    private HBox toogleCastrado;

    @FXML
    private Button voltarButton;

    @FXML
    private CheckBox sexoDesconhecidoCheckBox;
    
    private byte[] fotoAnimal;
    private String ultimoStatus;
    ToggleView toggleViewSexo;
    ToggleView toogleViewCastrado;
    ProcedimentoService procedimentoService;
    AnimalService animalService;
    
    private static Animal ultimoAnimal;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado) {
        initialize(dado);
        initializeViews(contentFather);
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(Object dado){
        ultimoAnimal = (dado != null) ? (Animal) dado : ultimoAnimal;
        animalService =  new AnimalService();
        procedimentoService = new ProcedimentoService();
        configuraToggles();
        setData(ultimoAnimal);  
    }
    
    public void initializeViews(Pane contentFather){
        criarGridProcedimentos(contentFather);
    }
    
    public void criarGridProcedimentos(Pane contentFather){
        List<Procedimento> procedimentos = procedimentoService.EncontrarProcedimentosPor(ultimoAnimal.getId());
        ProcedimentoGridView procedimentosGrid = new ProcedimentoGridView(procedimentosGridView, 1, procedimentos);
        procedimentosGrid.createGridAsync();   
    }
    
    public void configuraToggles(){
        toggleViewSexo = configuraToggleSexo(toggleSexo);
        toogleViewCastrado = configuraToggleCastrado(toogleCastrado);
    }
    
    public void setData(Animal animal){
        ImageLoader.CarregarImagem(imagemAnimal, animal.getFoto(), animal.idFoto(), Rectangles.GetRectangleImageAnimais());
        nomeAnimalTextField.setText(animal.getNome());
              
        Idade idadeAnimal = CalculaAnosEMesesPorDt(animal.getDataNascimento());
        
        anosAnimalTextField.setText(String.valueOf(idadeAnimal.getAnos()));
        mesesAnimalTextField.setText(String.valueOf(idadeAnimal.getMeses()));
        descricaoAnimalTextField.setText(animal.getDescricao());
        toggleViewSexo.ativarBotao((animal.getSexo() == 'M') ? DIREITO : ESQUERDO);
        toogleViewCastrado.ativarBotao((animal.isCastrado()) ? DIREITO : ESQUERDO);
    }
    
    public void atualizarAnimal(Stage primaryStage) {
        String nomeAnimal = nomeAnimalTextField.getText();
        String anosAnimal = anosAnimalTextField.getText();
        String mesesAnimal = mesesAnimalTextField.getText();
        String descricaoAnimal = descricaoAnimalTextField.getText();
        ToogleEnum sexoAnimal = toggleViewSexo.getSelectedItem();
        ToogleEnum castrado = toogleViewCastrado.getSelectedItem();

        animalService.Salvar(ultimoAnimal.getId() ,nomeAnimal, anosAnimal, mesesAnimal, descricaoAnimal, sexoAnimal, castrado, fotoAnimal, ultimoStatus);
    }
    
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow) {
        salvarAnimal.setOnMouseClicked(e -> {
            atualizarAnimal(primaryStage);
            App.getInstance().EntrarTelaInicial(contentFather, primaryStage, blackShadow);
        });

        layoutImageViewAnimal.setOnMouseClicked(e -> {
            fotoAnimal = CarregarImagemAnimal(primaryStage, imagemAnimal, layoutImageViewAnimal);
        });

        statusAnimal.getItems().forEach(item -> item.setOnAction(event -> {
            ultimoStatus = item.getText();
        }));
        
        voltarButton.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaInicial(contentFather, primaryStage, blackShadow);
        });
        
        adicionarProcedimentoButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComOrigemEDado(DIALOG_CADASTRAR_PROCEDIMENTO, FORM_ANIMAL_DETALHES , contentFather, primaryStage, blackShadow,
                    new Object[]{ultimoAnimal.getId()});
        });
        
        adotarButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_ADOCAO, contentFather, primaryStage, blackShadow);
        });
        
        removerButton.setOnMouseClicked(e ->{
            App.getInstance().AbrirDialogComOrigem(DIALOG_REMOVER, FORM_ANIMAL_DETALHES, contentFather, primaryStage, blackShadow);
        });       
        
        sexoDesconhecidoCheckBox.setOnAction(event -> {
            if (sexoDesconhecidoCheckBox.isSelected()) {
                toggleViewSexo.desativarToogle();
            }else{
                toggleViewSexo.ativarToogle();
            }
        });
    }
    
    @FXML
    public void handleKeyTypedAnos(KeyEvent event) {
        char input = event.getCharacter().charAt(0);

        if (!Character.isDigit(input) || anosAnimalTextField.getText().length() >= 2) {
              manterTexto(anosAnimalTextField);     
        } else {
            String currentText = anosAnimalTextField.getText();
            int ano = IntegerHelper.IntegerParse(currentText);
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
            int ano = IntegerHelper.IntegerParse(currentText);
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
         criarGridProcedimentos(contentFather);
    }
}
