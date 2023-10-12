package main.controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import main.interfaces.Inicializador;
import main.model.Animal;
import main.views.gridview.AnimalGridView;

public class HomeController implements Inicializador{
    
    @FXML
    private GridPane animaisGrid;
 
    @Override
    public void Inicializar(Pane contentFather) {
        List<Animal> animais = getListaDeAnimais();
        AnimalGridView animalGridView = new AnimalGridView(animaisGrid, 5, animais, contentFather);
        animalGridView.createGrid();
    }

    public static List<Animal> getListaDeAnimais() {
        List<Animal> animais = new ArrayList<>();

        animais.add(new Animal("Inacio", 3, "https://www.petz.com.br/blog//wp-content/uploads/2021/11/enxoval-para-gato-Copia.jpg"));
        animais.add(new Animal("Juninho", 5, "https://static.poder360.com.br/2020/10/gato-animal-covid-19-scaled.jpg"));
        animais.add(new Animal("Juquinha", 1, "https://super.abril.com.br/wp-content/uploads/2020/09/04-09_gato_SITE.jpg?quality=70&strip=info"));
        
        animais.add(new Animal("Inacio", 3, "https://www.petz.com.br/blog//wp-content/uploads/2021/11/enxoval-para-gato-Copia.jpg"));
        animais.add(new Animal("Juninho", 5, "https://static.poder360.com.br/2020/10/gato-animal-covid-19-scaled.jpg"));
        animais.add(new Animal("Juquinha", 1, "https://super.abril.com.br/wp-content/uploads/2020/09/04-09_gato_SITE.jpg?quality=70&strip=info"));
        
        animais.add(new Animal("Inacio", 3, "https://www.petz.com.br/blog//wp-content/uploads/2021/11/enxoval-para-gato-Copia.jpg"));
        animais.add(new Animal("Juninho", 5, "https://static.poder360.com.br/2020/10/gato-animal-covid-19-scaled.jpg"));
        animais.add(new Animal("Juquinha", 1, "https://super.abril.com.br/wp-content/uploads/2020/09/04-09_gato_SITE.jpg?quality=70&strip=info"));

        return FXCollections.observableArrayList(animais);
    } 

    

    
}
