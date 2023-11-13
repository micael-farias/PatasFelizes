package main.views.gridview;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public abstract class GridView<T> {

    private Insets insets = new Insets(0);
    private GridPane grid;
    private ProgressIndicator progressIndicator;
    StackPane stackPaneScroll;
    private int numColumns;
    private List<T> items;
    int column = 0;
    int row = 1;

    public GridView(GridPane grid, int numColumns, List<T> items) {
        this.grid = grid;
        this.numColumns = numColumns;
        this.items = items;
        this.progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(100, 100); // Definir largura e altura desejadas
    }

    public abstract Node createGridAsyncItem(T item, int column, int row);

    public Node itemInicial() {
        return null;
    }

    public GridPane createGrid() {
        Node primeiroItem = itemInicial();
        if (primeiroItem != null) {
            configurarItemGrid(primeiroItem);
        }

        for (T item : items) {
            Node gridItem = createGridAsyncItem(item, column, row);
            configurarItemGrid(gridItem);
        }

        // Remover o ProgressIndicator após o carregamento
        if(stackPaneScroll != null){
                    Platform.runLater(() -> stackPaneScroll.getChildren().remove(progressIndicator));

        }

        return grid;
    }
    
    public void set(StackPane stackPaneScroll){
        this.stackPaneScroll = stackPaneScroll;
    }

    public void configurarItemGrid(Node gridItem) {
        grid.add(gridItem, column, row);

        if (++column >= numColumns) {
            column = 0;
            row++;
        }

        GridPane.setMargin(gridItem, insets);
    }

    public void setInsets(Insets insets) {
        this.insets = insets;
    }

    public void createGridAsync() {
        // Adicionar o ProgressIndicator centralizado antes de iniciar a thread
            if(stackPaneScroll != null){      
                grid.getChildren().clear();
                stackPaneScroll.setAlignment(Pos.CENTER);
                stackPaneScroll.getChildren().add(progressIndicator);
            }

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {  
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> createGrid());
                return null;
            }
        };

        task.setOnSucceeded(null);

        Thread thread = new Thread(task);
        thread.start();
    }
}
