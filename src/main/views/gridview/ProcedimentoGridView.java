package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.App;
import main.controllers.AdicionarProcedimentoController;
import main.controllers.ProcedimentoController;
import main.model.Procedimento;
import static main.utils.Constantes.CARD_ADICIONAR_PROCEDIMENTO;
import static main.utils.Constantes.CARD_PROCEDIMENTO;

public class ProcedimentoGridView extends GridView<Procedimento> {     
            
    Pane contentFather;
    Pane blackShadow;
    Stage primaryStage;
    int idAnimal;
    
    public ProcedimentoGridView(Pane contentFather, Stage primmaryStage, Pane blackShadow, GridPane animaisGrid, int numColumns, List<Procedimento> items, int id) {
        super(animaisGrid, numColumns, items);
        this.contentFather = contentFather;
        this.blackShadow = blackShadow;
        this.primaryStage = primmaryStage;
        this.idAnimal = id;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Procedimento procedimento, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_PROCEDIMENTO, HBox.class);
        ProcedimentoController controller = fxmlLoader.getLoader().getController();
        controller.Inicializar(contentFather, primaryStage, blackShadow, new Object[]{procedimento,row});     
        return fxmlLoader.getResult();
    } 
    
    @Override
    public Node itemInicial() {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ADICIONAR_PROCEDIMENTO, HBox.class);    
        AdicionarProcedimentoController controller = fxmlLoader.getLoader().getController();
        controller.setOnClick(contentFather, primaryStage, blackShadow, idAnimal);              
        return fxmlLoader.getResult();
    }
}
