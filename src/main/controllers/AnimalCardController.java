package main.controllers;

import main.App;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.interfaces.InicializadorComDado;
import main.model.Animal;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.ImageCache;
import static main.utils.ImageLoader.CarregarImagem;

public class AnimalCardController implements InicializadorComDado{

    @FXML
    private Label idadeAnimal;
    @FXML
    private ImageView imagemAnimal;
    @FXML
    private Label nomeAnimal;
    @FXML
    private ImageView sexoAnimal;

    @Override
    public void Inicializar(Pane contentFather, Pane blackShadow, Object dado) {

        Animal animal = (Animal) dado;

        idadeAnimal.setText(String.valueOf(animal.getIdade()) + " meses");
        nomeAnimal.setText(animal.getNome());
        if(animal.getSexo() == 'M'){
            sexoAnimal.setImage(new Image(PATH_IMAGES + "marte-azul.png"));
        }else{
            sexoAnimal.setImage(new Image(PATH_IMAGES + "femea.png"));
        }
        
        Rectangle clip = new Rectangle(180, 200);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        CarregarImagem(imagemAnimal, animal.getFoto(), clip);
        imagemAnimal.setOnMouseClicked(e -> {
            App.getInstance().EntrarTela(FORM_ANIMAL_DETALHES, contentFather, animal, blackShadow);
        }); 
    }


}
