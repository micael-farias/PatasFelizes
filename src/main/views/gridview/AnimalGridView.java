package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import main.controllers.AdicionarAnimalController;
import main.controllers.AnimalCardController;
import main.model.Animal;
import static main.utils.Constantes.CARD_ADICIONAR_ANIMAL;
import static main.utils.Constantes.CARD_ANIMAL;

public class AnimalGridView extends GridView<Animal> {
    
    Pane contentFather;
    Pane blackShadow;
    
    public AnimalGridView(GridPane animaisGrid, int numColumns, List<Animal> items, Pane contentFather, Pane blackShadow) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        this.blackShadow = blackShadow;
        setInsets(new Insets(10));
    }

    @Override
    public Node createGridItem(Animal animal, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ANIMAL, VBox.class);
        AnimalCardController controller = fxmlLoader.getLoader().getController();
        controller.Inicializar(contentFather, blackShadow, animal);              
        return fxmlLoader.getResult();
    }

    @Override
    public Node itemInicial() {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ADICIONAR_ANIMAL, VBox.class);    
        AdicionarAnimalController controller = fxmlLoader.getLoader().getController();
        controller.setOnClick(contentFather, blackShadow);              
        return fxmlLoader.getResult();
    }
}

