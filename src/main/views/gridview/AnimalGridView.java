package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import main.controllers.AdicionarAnimalController;
import main.controllers.AnimalCardController;
import main.model.Animal;
import static main.utils.Constantes.CARD_ADICIONAR_ANIMAL;
import static main.utils.Constantes.CARD_ANIMAL;

public class AnimalGridView extends GridView<Animal> {
    
    Pane contentFather;
    Stage primmaryStage;
    Pane blackShadow;
    
    public AnimalGridView(GridPane animaisGrid, int numColumns, List<Animal> items, Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        this.blackShadow = blackShadow;
        this.primmaryStage = primmaryStage;
        setInsets(new Insets(10));
    }

    @Override
    public Node createGridAsyncItem(Animal animal, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ANIMAL, VBox.class);
        AnimalCardController controller = fxmlLoader.getLoader().getController();
        controller.Inicializar(contentFather, primmaryStage, blackShadow, animal);              
        return fxmlLoader.getResult();
    }

    @Override
    public Node itemInicial() {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ADICIONAR_ANIMAL, VBox.class);    
        AdicionarAnimalController controller = fxmlLoader.getLoader().getController();
        controller.setOnClick(contentFather, primmaryStage, blackShadow);              
        return fxmlLoader.getResult();
    }
}

