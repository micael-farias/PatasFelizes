package main.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import static main.controllers.AnimalFormularioController.CarregarImagem;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Animal;
import main.model.Idade;
import main.model.Procedimento;
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

public class CadastrarAnimalController extends AnimalFormularioController implements Inicializador{

   
    @FXML
    private TextField anosAnimalTextField;

    @FXML
    private TextArea descricaoAnimalTextField;

    @FXML
    private Button filtrarAnimaisButton;

    @FXML
    private ImageView imagemAnimal;

    @FXML
    private VBox layoutImageViewAnimal;

    @FXML
    private TextField mesesAnimalTextField;

    @FXML
    private TextField nomeAnimalTextField;

    @FXML
    private Button salvarAnimal;

    @FXML
    private ImageView sexoDesconhecidoCheckBox;

    @FXML
    private MenuButton statusAnimal;

    @FXML
    private HBox toggleSexo;

    @FXML
    private HBox toogleCastrado;
    
    @FXML
    private Button cancelarCadastro;
    
    private ToggleView toggleViewSexo;
    private ToggleView toogleViewCastrado;
    private char sexoAnimalValor;
    private byte[] fotoAnimal;
    private String ultimoStatus;
    private AnimalService animalService;
    
    private ProcedimentoService procedimentoService;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        initialize();
        setListeners(contentFather, primmaryStage, blackShadow);
    }
    
    public void initialize(){
        animalService =  new AnimalService();
        procedimentoService = new ProcedimentoService();
        configuraToggles();
    }
    
    public void configuraToggles(){
        toggleViewSexo = configuraToggleSexo(toggleSexo);
        toogleViewCastrado = configuraToggleCastrado(toogleCastrado);
    }
    

    public void salvarAnimal(Stage primaryStage) {
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

        animalService.Salvar(-1 ,nomeAnimal, anosAnimal, mesesAnimal, descricaoAnimal, sexoAnimal, castrado, fotoAnimal, ultimoStatus);
    }
    
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow) {
        salvarAnimal.setOnMouseClicked(e -> {
            salvarAnimal(primaryStage);
            App.getInstance().EntrarTelaInicial(contentFather, primaryStage, blackShadow);
        });

        layoutImageViewAnimal.setOnMouseClicked(e -> {
            fotoAnimal = CarregarImagem(primaryStage, imagemAnimal, layoutImageViewAnimal);
        });

        statusAnimal.getItems().forEach(item -> item.setOnAction(event -> {
            statusAnimal.setText(item.getText());
            ultimoStatus = item.getText();
        }));
        
        
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
    
    
    @FXML
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

}
