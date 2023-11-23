package main.controllers;

import java.util.Calendar;
import main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.interfaces.InicializadorComDado;
import main.model.Animal;
import main.model.Idade;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateHelper.CalculaAnosEMesesPorDt;
import static main.utils.ImageLoader.CarregarImagem;
import main.utils.Rectangles;

public class AnimalCardController extends CustomController implements InicializadorComDado{

    @FXML
    private Label idadeAnimal;
    @FXML
    private ImageView imagemAnimal;
    @FXML
    private Label nomeAnimal;
    @FXML
    private ImageView sexoAnimal;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object dado[]) {

        Animal animal = (Animal) ObterDadoArray(dado, 0);

        Idade idadeAnimalMesesAnos = CalculaAnosEMesesPorDt(animal.getDataNascimento());
        String textoIdadeAnimal = idadeAnimalMesesAnos.getAnos() + " anos e " + idadeAnimalMesesAnos.getMeses() + " meses";
        
        idadeAnimal.setText(textoIdadeAnimal);
        nomeAnimal.setText(animal.getNome());
        if(animal.getSexo() == 'M'){
            sexoAnimal.setImage(new Image(PATH_IMAGES + "marte-azul.png"));
        }else{
            sexoAnimal.setImage(new Image(PATH_IMAGES + "femea.png"));
        }
        
        
        CarregarImagem(imagemAnimal, animal.getFoto(), animal.idFoto(), Rectangles.GetRectangleImageAnimais());
        imagemAnimal.setOnMouseClicked(e -> {
            App.getInstance().EntrarTela(FORM_ANIMAL_DETALHES, contentFather, primmaryStage, new Object[]{animal}, blackShadow);
        }); 
    }


}
