package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import main.controllers.AnimalCardController;
import main.controllers.DespesaController;
import main.model.Animal;
import static main.utils.Constantes.CARD_DESPESA;
import main.utils.InicializarFormulario;


public class DespesasGridView extends GridView<Animal> {
    
    Pane contentFather;
            
    public DespesasGridView(GridPane animaisGrid, int numColumns, List<Animal> items, Pane contentFather) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridItem(Animal animal, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_DESPESA, HBox.class);
        DespesaController controller = fxmlLoader.getLoader().getController();
        controller.setData(row);     
        return fxmlLoader.getResult();
    }
    
}