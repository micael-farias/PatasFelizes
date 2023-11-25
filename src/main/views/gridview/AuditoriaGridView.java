package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import main.App;
import main.controllers.AuditoriaController;
import main.model.Alteracao;
import static main.utils.Constantes.CARD_AUDITORIA;


public class AuditoriaGridView extends GridView<Alteracao> {
    
 
    
    public AuditoriaGridView(GridPane auditoriaGrid, int numColumns, List<Alteracao> items,  StackPane stackPaneScroll) {
        super(auditoriaGrid, numColumns, items);
        set(stackPaneScroll);
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Alteracao alteracao, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_AUDITORIA, HBox.class);
        AuditoriaController controller = fxmlLoader.getLoader().getController();
        controller.setData(alteracao, row);
        return fxmlLoader.getResult();
    }
    
}