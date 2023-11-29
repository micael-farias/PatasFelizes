package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.App;
import main.controllers.FiltroAplicadoController;
import static main.utils.Constantes.FILTRO_APLICADO_VIEW;

public class FiltroGridView extends GridView<String> {
    

    
    
    public FiltroGridView(GridPane animaisGrid, int numColumns, List<String> filtros) {
        super(animaisGrid, numColumns, filtros);
        setInsets(new Insets(-20,10,10,0));
    }

    @Override
    public Node createGridAsyncItem(String filtro, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(FILTRO_APLICADO_VIEW, HBox.class);
        FiltroAplicadoController controller = fxmlLoader.getLoader().getController();
        controller.setData(filtro);
        return fxmlLoader.getResult();
    }
}

