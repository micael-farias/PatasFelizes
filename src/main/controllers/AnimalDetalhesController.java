package main.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import main.App;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.interfaces.InicializadorComDado;
import main.model.Animal;
import main.model.Procedimento;
import main.utils.ImageCache;
import static main.utils.ToogleEnum.DIREITO;
import static main.utils.ToogleEnum.ESQUERDO;
import main.views.gridview.ProcedimentoGridView;
import main.views.toggle.ToggleView;

public class AnimalDetalhesController implements InicializadorComDado{
    
   @FXML
    private TextArea descricaoAnimalTextField;

    @FXML
    private TextField idadeAnimalTextField;

    @FXML
    private ImageView imagemAnimal;

    @FXML
    private TextField nomeAnimalTextField;

    @FXML
    private HBox toggleSexo;

    @FXML
    private HBox toogleCastrado;

    @FXML
    private HBox toogleVermifugado;
        
    @FXML
    private Button voltarButton;
    
    @FXML
    private GridPane procedimentosGridView;

    ToggleView toggleViewSexo;
    ToggleView toogleViewCastrado;
    ToggleView toogleViewVermifugado;
    
    @Override
    public void Inicializar(Pane contentFather, Pane blackShadow, Object dado) {
        Animal animal = (Animal) dado;
        
        toggleViewSexo = new ToggleView();
        toogleViewCastrado = new ToggleView();
        toogleViewVermifugado = new ToggleView();
        
        toggleViewSexo.CriarToogle(toggleSexo);
        toogleViewCastrado.CriarToogle(toogleCastrado);
        toogleViewVermifugado.CriarToogle(toogleVermifugado);
        
        toggleViewSexo.setImagemDireita("marte-cinza.png");
        toggleViewSexo.setImagemEsquerda("venus-cinza.png");
        
        toogleViewCastrado.setTextoDireito("SIM");
        toogleViewCastrado.setTextoEsquerdo("NAO");
          
        toogleViewVermifugado.setTextoDireito("SIM");
        toogleViewVermifugado.setTextoEsquerdo("NAO");
        
        voltarButton.setOnMouseClicked(e ->{
            App.getInstance().EntrarTelaInicial(contentFather, blackShadow);
        });
        
        setData(animal);
        
        List<Procedimento> procedimentos = new ArrayList<>();
        procedimentos.add(new Procedimento("Procedimento 1", new Date()));
        procedimentos.add(new Procedimento("Procedimento 2", new Date()));
        procedimentos.add(new Procedimento("Procedimento 3", new Date()));
        procedimentos.add(new Procedimento("Procedimento 1", new Date()));
        procedimentos.add(new Procedimento("Procedimento 2", new Date()));
        procedimentos.add(new Procedimento("Procedimento 3", new Date()));
        procedimentos.add(new Procedimento("Procedimento 1", new Date()));
        procedimentos.add(new Procedimento("Procedimento 2", new Date()));
        procedimentos.add(new Procedimento("Procedimento 3", new Date()));
        
        ProcedimentoGridView procedimentosGrid = new ProcedimentoGridView(procedimentosGridView, 1, procedimentos, contentFather);
        procedimentosGrid.createGrid();
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
       
            Rectangle clip = new Rectangle(180, 225);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            imagemAnimal.setClip(clip);
        });

        loadImageTask.setOnFailed(event -> {
           //TODO: COLOCAR UMA IMAGEM PADRÃO
        });

        new Thread(loadImageTask).start();
    }
    
    public void setData(Animal animal){
        carregarImagem(animal.getFoto());
        nomeAnimalTextField.setText(animal.getNome());
        idadeAnimalTextField.setText(String.valueOf(animal.getIdade()));
        descricaoAnimalTextField.setText(animal.getDescricao());
        toggleViewSexo.ativarBotao((animal.getSexo() == 'M') ? DIREITO : ESQUERDO);
        toogleViewCastrado.ativarBotao((animal.isCastrado()) ? DIREITO : ESQUERDO);
        toogleViewVermifugado.ativarBotao((animal.isVermifugado()) ? DIREITO : ESQUERDO);    
    }
    







}