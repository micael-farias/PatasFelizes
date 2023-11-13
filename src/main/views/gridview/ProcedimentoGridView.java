package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import main.controllers.ProcedimentoController;
import main.model.Procedimento;
import static main.utils.Constantes.CARD_PROCEDIMENTO;


public class ProcedimentoGridView extends GridView<Procedimento> {
       
    Pane contentFather;
            
    public ProcedimentoGridView(GridPane animaisGrid, int numColumns, List<Procedimento> items, Pane contentFather) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Procedimento procedimento, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_PROCEDIMENTO, VBox.class);
        ProcedimentoController controller = fxmlLoader.getLoader().getController();
        controller.setData(row, procedimento);     
        return fxmlLoader.getResult();
    }
}
