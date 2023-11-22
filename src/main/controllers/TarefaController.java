/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorComDado;
import main.model.Despesa;
import main.model.Procedimento;
import main.model.Tarefa;
import main.services.TarefaServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import static main.utils.Constantes.DIALOG_CADASTRAR_TAREFA;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaString;
import static main.utils.DateHelper.DataParaString;
import main.utils.ImageLoader;
import static main.utils.ImageLoader.CarregarImagem;

public class TarefaController extends CustomController implements InicializadorComDado{
    
    @FXML
    private CheckBox checkBoxRealizado;
    
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
    
    private Tarefa tarefa;
    private TarefaServices tarefaServices;
    
     public void setData(Object[] dados) {
        tarefa = (Tarefa) ObterDadoArray(dados, 0);
        tarefaServices = new TarefaServices();
        
        int posicao = (int) ObterDadoArray(dados, 1);   
        
        Rectangle clip = new Rectangle(35, 35);
            clip.setArcWidth(10);
            clip.setArcHeight(10);

        CarregarImagem(fotoVoluntario, tarefa.getVoluntario().getFoto(), tarefa.getVoluntario().idFoto(), clip);
        descricaoTarefa.setText(tarefa.getDescricao());
        nomeAnimal.setText(tarefa.getAnimal()!= null ? tarefa.getAnimal().getNome() : "-");
        nomeVoluntario.setText(tarefa.getVoluntario().getNome());
        dataTarefa.setText(CalendarParaString(tarefa.getData()));
        dataTarefa.setStyle("-fx-font-weight: bold;");
        
        checkBoxRealizado.setSelected(tarefa.isRealizado());
        checkBoxRealizado.setOnAction(event -> {
            if (checkBoxRealizado.isSelected()) {         
                tarefaServices.Salvar(tarefa.getId(), tarefa.getVoluntario().getNome(), tarefa.getAnimal() != null ? tarefa.getAnimal().getNome() : null,
                        tarefa.getDescricao(), DateHelper.CalendarParaLocalDate(tarefa.getData()), tarefa.getTipo(), true);
            }else{
                             tarefaServices.Salvar(tarefa.getId(), tarefa.getVoluntario().getNome(), tarefa.getAnimal() != null ? tarefa.getAnimal().getNome() : null,
                        tarefa.getDescricao(), DateHelper.CalendarParaLocalDate(tarefa.getData()), tarefa.getTipo(), false);          
            }
        });   
        
        if(posicao % 2 == 0){
            layoutTarefa.setStyle("-fx-background-color: white;");
            editarTarefa.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirTarefa.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }
     
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow){
        tarefaLayout.setOnMouseClicked(e -> {
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_TAREFA , contentFather, primaryStage, blackShadow, new Object[]{ tarefa });        
        });      
    }
 
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    
    }   
    
   
}