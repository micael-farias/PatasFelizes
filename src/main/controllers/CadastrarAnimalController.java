package main.controllers;

import java.util.ArrayList;
import java.util.List;
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
import main.App;
import main.interfaces.Inicializador;
import main.interfaces.Resumidor;
import main.model.Procedimento;
import main.services.AnimalService;
import main.services.ProcedimentoService;
import static main.utils.Constantes.FORM_HOME;
import main.utils.NumberHelper;
import main.utils.ToogleEnum;
import main.views.gridview.ProcedimentoGridView;
import main.views.toggle.ToggleView;

public class CadastrarAnimalController extends AnimalFormularioController implements Inicializador{

    @FXML
    private Button adicionarProcedimentoButton;

    @FXML
    private TextArea descricaoAnimalTextField;

    @FXML
    private TextField anosAnimalTextField;

    @FXML
    private TextField mesesAnimalTextField;
    
    @FXML
    private ImageView imagemAnimal;

    @FXML
    private VBox layoutImageViewAnimal;

    @FXML
    private TextField nomeAnimalTextField;

    @FXML
    private Button salvarAnimal;

    @FXML
    private HBox toggleSexo;

    @FXML
    private HBox toogleCastrado;

    @FXML
    private MenuButton statusAnimal;
    
    @FXML
    private CheckBox sexoDesconhecidoCheckBox;
     
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
    public void Inicializar(Pane contentFather, Stage primaryStage, Pane blackShadow) {
        initialize();
        configuraToggles();
        setListeners(contentFather, primaryStage, blackShadow);
    }

    public void initialize() {
        animalService = new AnimalService();
        procedimentoService = new ProcedimentoService();
    }
    
    public void configuraToggles(){
        toggleViewSexo = configuraToggleSexo(toggleSexo);
        toogleViewCastrado = configuraToggleCastrado(toogleCastrado);
    }   
    
    public void cadastrarNovoAnimal(Stage primaryStage) {
        String nomeAnimal = nomeAnimalTextField.getText();
        String mesesAnimal = mesesAnimalTextField.getText();
        String anosAnimal = anosAnimalTextField.getText();
        String descricaoAnimal = descricaoAnimalTextField.getText();
        ToogleEnum sexoAnimal;
        if(sexoAnimalValor == 'N'){
            sexoAnimal = null;
        }else{
            sexoAnimal = toggleViewSexo.getSelectedItem();   
        } 
        
        ToogleEnum castrado = toogleViewCastrado.getSelectedItem();
        ultimoStatus = "F";
        animalService.Salvar(-1 , nomeAnimal, anosAnimal, mesesAnimal, descricaoAnimal, sexoAnimal, castrado, fotoAnimal, ultimoStatus);
        
    }

    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow) {
        salvarAnimal.setOnMouseClicked(e -> {
            cadastrarNovoAnimal(primaryStage);
            App.getInstance().EntrarTelaInicial(contentFather, primaryStage, blackShadow);
        });

        layoutImageViewAnimal.setOnMouseClicked(e -> {
            fotoAnimal = CarregarImagem(primaryStage, imagemAnimal, layoutImageViewAnimal);
        });

        statusAnimal.getItems().forEach(item -> item.setOnAction(event -> {
            ultimoStatus = item.getText();
        }));     
        
        cancelarCadastro.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaOnResume(FORM_HOME, contentFather, primaryStage, blackShadow, null);
        });
        
        sexoDesconhecidoCheckBox.setOnAction(event -> {
            if (sexoDesconhecidoCheckBox.isSelected()) {
                sexoAnimalValor = 'N';
                toggleViewSexo.desativarToogle();
            }else{
                sexoAnimalValor = toggleViewSexo.getSelectedItem() == ToogleEnum.DIREITO ? 'F' : 'M';
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
