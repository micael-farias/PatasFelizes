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
import main.controllers.AdicionarAnimalController;
import main.controllers.AdicionarDoacaoController;
import main.controllers.DespesaController;
import main.controllers.DoacaoController;
import main.model.Despesa;
import main.model.Doacao;
import static main.utils.Constantes.CARD_ADICIONAR_ANIMAL;
import static main.utils.Constantes.CARD_ADICIONAR_DOACAO;
import static main.utils.Constantes.CARD_DESPESA;
import static main.utils.Constantes.CARD_DOACAO;
import static main.utils.Constantes.DIALOG_CADASTRAR_DESPESA;
import static main.utils.Constantes.DIALOG_CADASTRAR_DOACAO;


public class DoacoesGridView extends GridView<Doacao> {
    
 
    Pane contentFather;
    Pane blackShadow;
    Stage primaryStage;
    
    public DoacoesGridView(GridPane animaisGrid, int numColumns, List<Doacao> items, Pane contentFather, StackPane stackPaneScroll, Stage primaryStage, Pane blackShadow) {
        super(animaisGrid, numColumns, items);
        set(stackPaneScroll);
        this.contentFather = contentFather;
        this.blackShadow = blackShadow;
        this.primaryStage = primaryStage;
        setInsets(new Insets(0,10,0,10));
    }

    @Override
    public Node createGridAsyncItem(Doacao doacao, int column, int row) {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_DOACAO, VBox.class);
        DoacaoController controller = fxmlLoader.getLoader().getController();
        controller.Inicializar(contentFather, primaryStage, blackShadow, new Object[]{doacao, row});
        return fxmlLoader.getResult();
    }
    
    @Override
    public Node itemInicial() {
        var fxmlLoader = App.getInstance().RealizarLoadFXML(CARD_ADICIONAR_DOACAO, HBox.class);    
        AdicionarDoacaoController controller = fxmlLoader.getLoader().getController();
        controller.setOnClick(contentFather, primaryStage, blackShadow, "Clique aqui para adicionar uma nova doação", DIALOG_CADASTRAR_DOACAO);              
        return fxmlLoader.getResult();
    }
    
    
}