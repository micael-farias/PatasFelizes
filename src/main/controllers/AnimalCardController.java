package main.controllers;

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
import main.utils.ImageCache;
import main.utils.InicializarFormulario;

public class AnimalCardController implements InicializadorComDado{

    @FXML
    private Label idadeAnimal;
    @FXML
    private ImageView imagemAnimal;
    @FXML
    private Label nomeAnimal;
    @FXML
    private ImageView sexoAnimal;
    InicializarFormulario inicializar = new InicializarFormulario();  

    @Override
    public void Inicializar(Pane contentFather, Object dado) {

        Animal animal = (Animal) dado;

        idadeAnimal.setText(String.valueOf(animal.getIdade()) + " meses");
        nomeAnimal.setText(animal.getNome());
        carregarImagem(animal.getFoto());
        imagemAnimal.setOnMouseClicked(e -> {
            inicializar.EntrarTela(FORM_ANIMAL_DETALHES, contentFather, nomeAnimal);
        }); 
    }

     public void carregarImagem(String foto){
        Task<Image> loadImageTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return ImageCache.loadImage(foto);
            }
        };

        loadImageTask.setOnSucceeded(event -> {
            Image loadedImage = loadImageTask.getValue();
            imagemAnimal.setImage(loadedImage);

            Rectangle clip = new Rectangle(180, 200);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            imagemAnimal.setClip(clip);
        });

        loadImageTask.setOnFailed(event -> {
           //TODO: COLOCAR UMA IMAGEM PADRÃO
        });

        new Thread(loadImageTask).start();
    }

}
