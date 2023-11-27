package main.controllers;

import main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.factories.StatusAnimalFactory;
import main.interfaces.InicializadorComDado;
import main.model.Animal;
import main.model.Idade;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
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
    private Circle statusAnimal;
    @FXML
    private Label nomeAnimal;

    @FXML
    private Label sexoAnimal;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado[]) {

        Animal animal = (Animal) ObterDadoArray(dado, 0);
      
        String textoIdadeAnimal;

        if(animal.getDataNascimento() == null){
             Idade idadeAnimalMesesAnos = CalculaAnosEMesesPorDt(animal.getDataNascimento());
             textoIdadeAnimal = idadeAnimalMesesAnos.getAnos() + " anos e " + idadeAnimalMesesAnos.getMeses() + " meses";
        }else{
             textoIdadeAnimal = "Não informada";
        }
        idadeAnimal.setText(textoIdadeAnimal);
        nomeAnimal.setText(animal.getNome());
        statusAnimal.setFill(StatusAnimalFactory.GetColorStatus(animal.getStatus()));
        switch (animal.getSexo()) {
            case 'M':
                sexoAnimal.setText("Macho");
                break;
            case 'F':
                sexoAnimal.setText("Femea");
                break;
            default:
                sexoAnimal.setText("Não identificado");
                break;
        }
        
        CarregarImagem(imagemAnimal, animal.getFoto(), animal.idFoto(), Rectangles.GetRectangleImageAnimais());
        pane.setClip(Rectangles.GetRectanglePaneAnimais());
        stackPane.setOnMouseClicked(e -> {
            App.getInstance().EntrarTela(FORM_ANIMAL_DETALHES, contentFather, primmaryStage, new Object[]{animal}, blackShadow);
        }); 
    }
    
   

}
