package main.views.gridview;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
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
        if (stackPaneScroll != null) {
            grid.getChildren().clear();
            stackPaneScroll.setAlignment(Pos.CENTER);
            stackPaneScroll.getChildren().add(progressIndicator);
        }  
        Node primeiroItem = itemInicial();
                if (primeiroItem != null) {
                    configurarItemGrid(primeiroItem);
                }    


       Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                int counter = 0;
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GridView.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                           
                


                for (T item : items) {
                    Node gridItem = createGridAsyncItem(item, column, counter++);
                    Platform.runLater(() -> configurarItemGrid(gridItem));
                }

                return null;
            }
        };

        task.setOnSucceeded(event -> {
            // Remover o ProgressIndicator após o carregamento
            if (stackPaneScroll != null) {
                stackPaneScroll.getChildren().remove(progressIndicator);
            }
        });
        
        task.setOnFailed(e ->{
                if (stackPaneScroll != null) {
                stackPaneScroll.getChildren().remove(progressIndicator);
            }
        });
        

        Thread thread = new Thread(task);
        thread.start();
    }
    
    

}
