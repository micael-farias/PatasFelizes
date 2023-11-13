package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.App;
import main.controllers.DespesaController;
import main.model.Despesa;
import static main.utils.Constantes.CARD_DESPESA;


public class DespesasGridView extends GridView<Despesa> {
    
    Pane contentFather;
            
    public DespesasGridView(GridPane animaisGrid, int numColumns, List<Despesa> items, Pane contentFather, StackPane stackPaneScroll) {
        super(animaisGrid, numColumns, items);
        set(stackPaneScroll);
        this.contentFather = contentFather;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Despesa despesa, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_DESPESA, VBox.class);
        DespesaController controller = fxmlLoader.getLoader().getController();
        controller.setData(row, despesa);     
        return fxmlLoader.getResult();
    }
    
}