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
import main.enums.MensagemTipo;
import main.interfaces.InicializadorComDado;
import main.model.Despesa;
import main.model.Procedimento;
import main.model.Tarefa;
import main.services.TarefaServices;
import static main.utils.Constantes.DIALOG_CADASTRAR_DESPESA;
import static main.utils.Constantes.DIALOG_CADASTRAR_PROCEDIMENTO;
import static main.utils.Constantes.DIALOG_CADASTRAR_TAREFA;
import static main.utils.Constantes.DIALOG_REMOVER;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
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
    
    private Procedimento procedimento;
    private TarefaServices tarefaServices;
    
     public void setData(Object[] dados) {
        procedimento = (Procedimento) ObterDadoArray(dados, 0);
        tarefaServices = new TarefaServices();
        
        int posicao = (int) ObterDadoArray(dados, 1);   
        
        Rectangle clip = new Rectangle(35, 35);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            
            
        CarregarImagem(fotoVoluntario, procedimento.getVoluntario() == null ? null : procedimento.getVoluntario().getFoto(),
                procedimento.getVoluntario() == null ? null : procedimento.getVoluntario().idFoto(), clip);
        descricaoTarefa.setText(procedimento.getDescricao());
        nomeAnimal.setText(procedimento.getAnimal()!= null ? procedimento.getAnimal().getNome() : "-");
        nomeVoluntario.setText(procedimento.getVoluntario() == null ? null : procedimento.getVoluntario().getNome());
        dataTarefa.setText(CalendarParaString(procedimento.getData()) + "-" +DateHelper.CalendarParaStringReduced(procedimento.getDataCadastro()));

       setImage(procedimento.isRealizado());
        checkBoxRealizado.setOnMouseClicked(event -> {
            boolean realizada = !procedimento.isRealizado();
            setImage(realizada);
            Procedimento procedimentoObtido;
            if (realizada) {  
                procedimentoObtido = tarefaServices.Salvar(procedimento.getId(),
                        procedimento.getVoluntario() == null ? null : procedimento.getVoluntario().getNome(),
                        procedimento.getAnimal() != null ? procedimento.getAnimal().getNome() : null,
                        procedimento.getDescricao(),
                        DateHelper.CalendarParaLocalDate(procedimento.getData()),
                        procedimento.getTipo(), true);
            }else{
                procedimentoObtido = tarefaServices.Salvar(procedimento.getId(),
                        procedimento.getVoluntario() == null ? null : procedimento.getVoluntario().getNome(),
                        procedimento.getAnimal() != null ? procedimento.getAnimal().getNome() : null,
                        procedimento.getDescricao(),
                        DateHelper.CalendarParaLocalDate(procedimento.getData()), procedimento.getTipo(), false);       
            }
            
            if(procedimentoObtido == null){
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao alterar o estado da tarefa", null);
            }else{
                procedimento= procedimentoObtido;
            }
        });   
        
        if(posicao % 2 == 0){
            layoutTarefa.setStyle("-fx-background-color: white;");
            editarTarefa.setImage(new Image(PATH_IMAGES +"editar-colorido.png"));
            excluirTarefa.setImage(new Image(PATH_IMAGES + "remover-colorido.png"));
        }           
    }
     
    public void setListeners(Pane contentFather, Stage primaryStage, Pane blackShadow){
        layoutClickable.setOnMouseClicked(e -> {
            App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_TAREFA , contentFather, primaryStage, blackShadow, new Object[]{ procedimento });        
        });  
        
        excluirTarefa.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComAcao(DIALOG_REMOVER, FORM_TAREFAS, contentFather, primaryStage, blackShadow,
                   new Object[]{ "Deseja realmente excluir essa tarefa? "}, (dados) ->{
                       if(tarefaServices.Excluir(procedimento.getId()) == 1){
                           App.getInstance().EntrarTelaOnResume(FORM_TAREFAS, contentFather, primaryStage, blackShadow, null);
                       }
                   });
        });
        
        editarTarefa.setOnMouseClicked(e ->{
          App.getInstance().AbrirDialogComDado(DIALOG_CADASTRAR_TAREFA, contentFather, primaryStage, blackShadow,
                   new Object[]{ procedimento.getId(), procedimento});    
        });
    }
 
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, Object[] dados) {
        setData(dados);
        setListeners(contentFather, primmaryStage, blackShadow);    
    }   
    
     public void setImage(boolean realizado){
        if(realizado){
            checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_cinza_checked.png"));          
        }else{
            checkBoxRealizado.setImage(new Image(PATH_IMAGES + "check_cinza_not_checked.png"));
        }
    }
    
   
}