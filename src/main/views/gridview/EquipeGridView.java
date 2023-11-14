package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.App;
import main.controllers.AdicionarAnimalController;
import main.controllers.VoluntarioController;
import main.model.Voluntario;
import static main.utils.Constantes.CARD_ADICIONAR_ANIMAL;
import static main.utils.Constantes.CARD_ADICIONAR_VOLUNTARIO;
import static main.utils.Constantes.CARD_VOLUNTARIO;


public class EquipeGridView extends GridView<Voluntario> {
    
    Pane contentFather;
            
    public EquipeGridView(GridPane animaisGrid, int numColumns, List<Voluntario> items, Pane contentFather, StackPane stackPaneScroll) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        set(stackPaneScroll);
        setInsets(new Insets(10));
    }

    @Override
    public Node createGridAsyncItem(Voluntario despesa, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_VOLUNTARIO, VBox.class);
        VoluntarioController controller = fxmlLoader.getLoader().getController();
        controller.setData(row, despesa);     
        return fxmlLoader.getResult();
    }
    
     @Override
    public Node itemInicial() {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ADICIONAR_VOLUNTARIO, VBox.class);    
        AdicionarAnimalController controller = fxmlLoader.getLoader().getController();
        //controller.setOnClick(contentFather, primmaryStage, blackShadow);              
        return fxmlLoader.getResult();
    }
}