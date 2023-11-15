package main.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Inicializador;
import main.model.Animal;
import main.model.Despesa;
import main.model.Tarefa;
import main.model.Voluntario;
import static main.utils.Constantes.DIALOG_CADASTRAR_TAREFA;
import static main.utils.Constantes.PATH_IMAGES;

import main.views.gridview.DespesasGridView;
import main.views.gridview.TarefasGridView;

public class TarefasController implements Inicializador {  

    @FXML
    private Button novaTarefa;

    @FXML
    private GridPane tarefasGrid;
    
    @FXML
    private StackPane stackPaneScroll;

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        List<Tarefa> despesas = new ArrayList<>();
        
        despesas.add(new Tarefa(1,new Voluntario("Alexandre Toledo", PATH_IMAGES +"alexandre.jpeg", "alexandre@toledo.com.br","85997654398"), new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),"Alimentação", new Date()));
        despesas.add(new Tarefa(
                2,
                new Voluntario("Dinah Toledo", PATH_IMAGES +"dina.jpeg", "dinah@toledo.com.br","85997654398"),
                new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),
                "Vacina", 
                new Date()));
        despesas.add(new Tarefa(3,new Voluntario("Pedro Emanuel", PATH_IMAGES+"pedro.png", "pedro@patasfelizes.com.br","85997654398"),new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),"Remédio", new Date()));
           despesas.add(new Tarefa(1,new Voluntario("Alexandre Toledo", PATH_IMAGES +"alexandre.jpeg", "alexandre@toledo.com.br","85997654398"), new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),"Alimentação", new Date()));
        despesas.add(new Tarefa(
                2,
                new Voluntario("Dinah Toledo", PATH_IMAGES +"dina.jpeg", "dinah@toledo.com.br","85997654398"),
                new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),
                "Vacina", 
                new Date()));
        despesas.add(new Tarefa(3,new Voluntario("Pedro Emanuel", PATH_IMAGES+"pedro.png", "pedro@patasfelizes.com.br","85997654398"),new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),"Remédio", new Date()));
            despesas.add(new Tarefa(1,new Voluntario("Alexandre Toledo", PATH_IMAGES +"alexandre.jpeg", "alexandre@toledo.com.br","85997654398"), new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),"Alimentação", new Date()));
        despesas.add(new Tarefa(
                2,
                new Voluntario("Dinah Toledo", PATH_IMAGES +"dina.jpeg", "dinah@toledo.com.br","85997654398"),
                new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),
                "Vacina", 
                new Date()));
        despesas.add(new Tarefa(3,new Voluntario("Pedro Emanuel", PATH_IMAGES+"pedro.png", "pedro@patasfelizes.com.br","85997654398"),new Animal('F', "Agnetha", 3, "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."),"Remédio", new Date()));
            
        
        novaTarefa.setOnMouseClicked(e->{
            App.getInstance().AbrirDialog(DIALOG_CADASTRAR_TAREFA, contentFather, primmaryStage, blackShadow);
        });
        TarefasGridView animalGridView = new TarefasGridView(tarefasGrid, 1, despesas, contentFather, stackPaneScroll);
        animalGridView.createGridAsync();
    }
  
}
