package main.views.gridview;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.controllers.AdicionarAnimalController;
import main.controllers.AnimalCardController;
import main.model.Animal;
import main.utils.InicializarFormulario;
import static main.utils.Constantes.CARD_ADICIONAR_ANIMAL;
import static main.utils.Constantes.CARD_ANIMAL;

public class AnimalGridView extends GridView<Animal> {
    
    InicializarFormulario inicializar = new InicializarFormulario();
    Pane contentFather;
            
    public AnimalGridView(GridPane animaisGrid, int numColumns, List<Animal> items, Pane contentFather) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
    }

    @Override
    public Node createGridItem(Animal animal, int column, int row) {
        var fxmlLoader = inicializar.RealizarLoadFXML(CARD_ANIMAL, VBox.class);
        AnimalCardController controller = fxmlLoader.getLoader().getController();
        controller.Inicializar(contentFather, animal);              
        return fxmlLoader.getResult();
    }

    @Override
    public Node itemInicial() {
        var fxmlLoader = inicializar.RealizarLoadFXML(CARD_ADICIONAR_ANIMAL, VBox.class);    
        AdicionarAnimalController controller = fxmlLoader.getLoader().getController();
        controller.setOnClick();              
        return fxmlLoader.getResult();
    }
}

