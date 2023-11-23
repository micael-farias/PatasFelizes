package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import main.controllers.DespesaController;
import main.controllers.TarefaController;
import main.model.Tarefa;
import static main.utils.Constantes.CARD_TAREFA;


public class TarefasGridView extends GridView<Tarefa> {
    
    Pane contentFather;
    Stage primmaryStage;
    Pane blackShadow;
            
    public TarefasGridView(Pane contentFather, Stage primmaryStage, Pane blackShadow, GridPane tarefasGrid, int numColumns, List<Tarefa> items, StackPane stackPaneScroll) {
        super(tarefasGrid, numColumns, items);
        this.contentFather = contentFather;
        this.blackShadow = blackShadow;
        this.primmaryStage = primmaryStage;
        set(stackPaneScroll);
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Tarefa tarefa, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_TAREFA, HBox.class);
        TarefaController controller = fxmlLoader.getLoader().getController();
        controller.Inicializar(contentFather, primmaryStage, blackShadow, new Object[]{tarefa, row});
                
        return fxmlLoader.getResult();
    }
    
}