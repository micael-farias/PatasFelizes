package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import main.controllers.AdicionarAnimalController;
import main.controllers.AdicionarVoluntarioController;
import main.controllers.VoluntarioController;
import main.model.Voluntario;
import static main.utils.Constantes.CARD_ADICIONAR_ANIMAL;
import static main.utils.Constantes.CARD_ADICIONAR_VOLUNTARIO;
import static main.utils.Constantes.CARD_VOLUNTARIO;


public class EquipeGridView extends GridView<Voluntario> {
    
    Pane contentFather;
    Pane blackShadow;
    Stage primaryStage;
            
    public EquipeGridView(GridPane animaisGrid, int numColumns, List<Voluntario> items, Pane contentFather, StackPane stackPaneScroll, Stage primaryStage, Pane blackShadow) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        this.blackShadow = blackShadow;
        this.primaryStage = primaryStage;
        set(stackPaneScroll);
        setInsets(new Insets(10));
    }

    @Override
    public Node createGridAsyncItem(Voluntario voluntario, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_VOLUNTARIO, VBox.class);
        VoluntarioController controller = fxmlLoader.getLoader().getController();
        controller.Inicializar(contentFather, primaryStage, blackShadow, new Object[]{ voluntario });     
        return fxmlLoader.getResult();
    }
    
     @Override
    public Node itemInicial() {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ADICIONAR_VOLUNTARIO, VBox.class);    
        AdicionarVoluntarioController controller = fxmlLoader.getLoader().getController();
        controller.setOnClick(contentFather, primaryStage, blackShadow);              
        return fxmlLoader.getResult();
    }
    
    @Override
    public Insets firstRowInsets() {
        return new Insets(-10,10,10,10);
    }

}