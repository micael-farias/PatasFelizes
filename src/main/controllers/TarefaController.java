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
import main.model.Voluntario;
import main.services.TarefaServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_DESPESA;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import static main.utils.Constantes.DIALOG_CADASTRAR_TAREFA;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.Constantes.FORM_FINANCAS;
import static main.utils.Constantes.FORM_TAREFAS;
import static main.utils.Constantes.PATH_IMAGES;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaString;
import static main.utils.DateHelper.DataParaString;
import main.utils.ImageLoader;
import static main.utils.ImageLoader.CarregarImagem;

public class TarefaController extends CustomController implements InicializadorComDado{
    
    @FXML
    private ImageView checkBoxRealizado;
    
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
    private HBox layoutClickable;
    
    private Tarefa tarefa;
    private TarefaServices tarefaServices;
    
     public void setData(Object[] dados) {
        tarefa = (Tarefa) ObterDadoArray(dados, 0);
        tarefaServices = new TarefaServices();
        
        int posicao = (int) ObterDadoArray(dados, 1);   
        
        Rectangle clip = new Rectangle(35, 35);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            
        Voluntario voluntario = tarefa.getVoluntario();
        byte[] foto = (voluntario == null) ? null : voluntario.getFoto();
        String idFoto = (voluntario == null) ? null : voluntario.idFoto();
        String nome = (voluntario == null) ? null : voluntario.getNome();
        CarregarImagem(fotoVoluntario, foto , idFoto, clip);
        descricaoTarefa.setText(tarefa.getDescricao());
        nomeAnimal.setText(tarefa.getAnimal()!= null ? tarefa.getAnimal().getNome() : "-");
        nomeVoluntario.setText(nome);
        dataTarefa.setText(CalendarParaString(tarefa.getData()));

        setImage(tarefa.isRealizada());
        checkBoxRealizado.setOnMouseClicked(event -> {
            boolean realizada = !tarefa.isRealizada();
            setImage(realizada);
            if (realizada) {  
                tarefa = tarefaServices.Salvar(tarefa.getId(), tarefa.getVoluntario().getNome(), tarefa.getAnimal() != null ? tarefa.getAnimal().getNome() : null,
                        tarefa.getDescricao(), DateHelper.CalendarParaLocalDate(tarefa.getData()), tarefa.getTipo(), true);
            }else{
                tarefa = tarefaServices.Salvar(tarefa.getId(), tarefa.getVoluntario().getNome(), tarefa.getAnimal() != null ? tarefa.getAnimal().getNome() : null,
                        tarefa.getDescricao(), DateHelper.CalendarParaLocalDate(tarefa.getData()), tarefa.getTipo(), false);       
            }
        });   
        
        if(posicao % 2 != 0){
            layoutTarefa.setStyle("-fx-background-color: white;");
            editarTarefa.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirTarefa.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }
     
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow){
        layoutClickable.setOnMouseClicked(e -> {
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_TAREFA , contentFather, primaryStage, blackShadow, new Object[]{ tarefa });        
        });  
        
        excluirTarefa.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComOrigemEDado(DIALOG_REMOVER, FORM_TAREFAS, contentFather, primaryStage, blackShadow,
                   new Object[]{ "Deseja realmente excluir essa tarefa? "});        
          });  
        
        editarTarefa.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_TAREFA, contentFather, primaryStage, blackShadow,
                   new Object[]{ tarefa.getId(), tarefa});    
        });
    }
 
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    
    }   
    
     public void setImage(boolean realizado){
        if(realizado){
            checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_azul_checked.png"));          
        }else{
            checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_azul_not_checked.png"));
        }
    }
    
   
}