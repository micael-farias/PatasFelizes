package main.views.gridview;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public abstract class GridView<T> {
    
    private GridPane grid;
    private int numColumns;
    private List<T> items;
    int column = 0;
    int row = 1;
    
    public GridView(GridPane grid, int numColumns, List<T> items) {
        this.grid = grid;
        this.numColumns = numColumns;
        this.items = items;
    }

    public abstract Node createGridItem(T item, int column, int row);
    
    public Node itemInicial(){
        return null;
    }

    public GridPane createGrid() {
            
        Node primeiroItem = itemInicial();     
        if(primeiroItem != null){
            configurarItemGrid(primeiroItem);
        }

        for (T item : items) {    
            Node gridItem = createGridItem(item, column, row);                  
            configurarItemGrid(gridItem);    
        }

        return grid;
    }
    
    public void configurarItemGrid(Node gridItem){
        grid.add(gridItem, column, row);

        if (++column >= numColumns) {
            column = 0;
            row++;
        }

        GridPane.setMargin(gridItem, new Insets(10));     
    }
}

