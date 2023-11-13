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
import main.controllers.DoacaoController;
import main.model.Despesa;
import main.model.Doacao;
import static main.utils.Constantes.CARD_DESPESA;
import static main.utils.Constantes.CARD_DOACAO;


public class DoacoesGridView extends GridView<Doacao> {
    
    Pane contentFather;
            
    public DoacoesGridView(GridPane animaisGrid, int numColumns, List<Doacao> items, Pane contentFather, StackPane stackPaneScroll) {
        super(animaisGrid, numColumns, items);
                set(stackPaneScroll);
this.contentFather = contentFather;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Doacao doacao, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_DOACAO, VBox.class);
        DoacaoController controller = fxmlLoader.getLoader().getController();
        controller.setData(row, doacao);     
        return fxmlLoader.getResult();
    }
    
}