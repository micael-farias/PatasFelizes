/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import main.model.Despesa;
import main.model.Tarefa;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.DateToString.DataParaString;
import main.utils.ImageLoader;
import static main.utils.ImageLoader.CarregarImagem;

public class TarefaController {
    
     @FXML
    private Label dataTarefa;

    @FXML
    private Label descricaoTarefa;

    @FXML
    private ImageView editarTarefa;

    @FXML
    private ImageView excluirTarefa;

    @FXML
    private ImageView fotoVoluntario;

    @FXML
    private HBox layoutTarefa;

    @FXML
    private Label nomeAnimal;

    @FXML
    private Label nomeVoluntario;

    @FXML
    private VBox tarefaLayout;
    
     public void setData(int posicao, Object dado) {
        Tarefa tarefa = (Tarefa) dado;
        
        Rectangle clip = new Rectangle(35, 35);
            clip.setArcWidth(10);
            clip.setArcHeight(10);

        CarregarImagem(fotoVoluntario, tarefa.getVoluntário().getFoto(), clip);
        descricaoTarefa.setText(tarefa.getDescricaoTarefa());
        nomeAnimal.setText(tarefa.getAnimal().getNome());
        nomeVoluntario.setText(tarefa.getVoluntário().getNome());
        dataTarefa.setText(DataParaString(tarefa.getData()));
        dataTarefa.setStyle("-fx-font-weight: bold;");

        
        if(posicao % 2 == 0){
            layoutTarefa.setStyle("-fx-background-color: white;");
            editarTarefa.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirTarefa.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }
}
