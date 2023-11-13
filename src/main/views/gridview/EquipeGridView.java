package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import main.controllers.VoluntarioController;
import main.model.Voluntario;
import static main.utils.Constantes.CARD_VOLUNTARIO;


public class EquipeGridView extends GridView<Voluntario> {
    
    Pane contentFather;
            
    public EquipeGridView(GridPane animaisGrid, int numColumns, List<Voluntario> items, Pane contentFather) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Voluntario despesa, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_VOLUNTARIO, VBox.class);
        VoluntarioController controller = fxmlLoader.getLoader().getController();
        controller.setData(row, despesa);     
        return fxmlLoader.getResult();
    }
    
}