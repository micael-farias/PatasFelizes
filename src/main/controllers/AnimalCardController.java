package main.controllers;

import main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.factories.StatusAnimalFactory;
import main.interfaces.InicializadorComDado;
import main.model.Animal;
import main.model.Idade;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalculaAnosEMesesPorDt;
import static main.utils.ImageLoader.CarregarImagem;
import main.utils.Rectangles;

public class AnimalCardController extends CustomController implements InicializadorComDado{

    @FXML
    private Label idadeAnimal;
    
    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView imagemAnimal;

    @FXML
    private Pane pane;
    
     @FXML
    private HBox statusAnimal;

    @FXML
    private Label statusAnimalLabel;
    
    @FXML
    private Label nomeAnimal;

    @FXML
    private Label sexoAnimal;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado[]) {

        Animal animal = (Animal) ObterDadoArray(dado, 0);
      
        String textoIdadeAnimal;
             
        Idade idadeAnimalMesesAnos= CalculaAnosEMesesPorDt(animal.getDataNascimento());

        if(idadeAnimalMesesAnos != null){
            
            if(idadeAnimalMesesAnos.getAnos() == 0 && idadeAnimalMesesAnos.getMeses() == 0){
                         textoIdadeAnimal = "Recém nascido";
            }
            else if(idadeAnimalMesesAnos.getAnos() == 0){
                 textoIdadeAnimal = idadeAnimalMesesAnos.getMeses()+ " meses";
             }else if(idadeAnimalMesesAnos.getMeses() == 0){
                 textoIdadeAnimal = idadeAnimalMesesAnos.getAnos()+ " anos";
             }else{
                 textoIdadeAnimal = idadeAnimalMesesAnos.getAnos() + " anos e " + idadeAnimalMesesAnos.getMeses() + " meses";   
             }
             
        }else{
             textoIdadeAnimal = "Idade não informada";
        }
        idadeAnimal.setText(textoIdadeAnimal);
        nomeAnimal.setText(animal.getNome());
        String statusColor = StatusAnimalFactory.GetColorStatus(animal.getStatus());
        statusAnimal.setStyle("-fx-background-color: " + statusColor+ " ; -fx-background-radius: 10px");
        statusAnimalLabel.setStyle("-fx-text-fill: white;");
        statusAnimalLabel.setText(StatusAnimalFactory.GetStatusString(animal.getStatus()));
        switch (animal.getSexo()) {
            case 'M':
                sexoAnimal.setText("Macho");
                break;
            case 'F':
                sexoAnimal.setText("Femea");
                break;
            default:
                sexoAnimal.setText("Sexo não identificado");
                break;
        }
        CarregarImagem(imagemAnimal, animal.getFoto(), animal.idFoto(), Rectangles.GetRectangleImageAnimais());
        pane.setClip(Rectangles.GetRectanglePaneAnimais());
        stackPane.setOnMouseClicked(e -> {
            App.getInstance().EntrarTela(FORM_ANIMAL_DETALHES, contentFather, primmaryStage, new Object[]{animal}, blackShadow);
        }); 
    }
    
   

}
