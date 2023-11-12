package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.App;
import main.controllers.DespesaController;
import main.controllers.TarefaController;
import main.model.Tarefa;
import static main.utils.Constantes.CARD_TAREFA;


public class TarefasGridView extends GridView<Tarefa> {
    
    Pane contentFather;
            
    public TarefasGridView(GridPane tarefasGrid, int numColumns, List<Tarefa> items, Pane contentFather) {
        super(tarefasGrid, numColumns, items);
        this.contentFather = contentFather;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridItem(Tarefa tarefa, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_TAREFA, VBox.class);
        TarefaController controller = fxmlLoader.getLoader().getController();
        controller.setData(row, tarefa);     
        return fxmlLoader.getResult();
    }
    
}