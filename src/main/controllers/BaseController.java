package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.App;
import main.interfaces.InicializadorBase;
import main.utils.Constantes;
import static main.utils.Constantes.*;

public class BaseController implements InicializadorBase{
    
    @FXML
    private Button menuButtonActive;
    @FXML
    private Button menuButtonPets;
    @FXML
    protected Pane content;   
    @FXML
    private Pane blackShadow;
    
    private Stage primmaryStage;
         
    
    @Override
    public void Inicializar(Stage primmaryStage) {
        this.primmaryStage = primmaryStage;
        App.getInstance().EntrarTelaInicial(content, primmaryStage, blackShadow);
        setActive(menuButtonPets);     
    }
   
    @FXML
    private void menuButtonPetsExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        toExitedStyle(button);
    }

    @FXML
    private void menuButtonPetsEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        toEnteredStyle(button);
    }

    @FXML
    private void menuButtonPetsClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        if(App.getInstance().MappingContatis(FORM_ANIMAL_DETALHES)){
            App.getInstance().EntrarTelaOnResume(FORM_ANIMAL_DETALHES, content, primmaryStage, blackShadow, null);
        }else{
            App.getInstance().EntrarTelaInicial(content, primmaryStage, blackShadow);
       
        }
        setActive(button);  
    }


    @FXML
    private void menuButtonAgendaExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        toExitedStyle(button);
    }

    @FXML
    private void menuButtonAgendaEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        toEnteredStyle(button);
    }

    @FXML
    private void menuButtonAgendaClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        App.getInstance().EntrarTela(FORM_TAREFAS, content, primmaryStage, blackShadow);
        setActive(button);
    }

    @FXML
    private void menuButtonEquipeExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        toExitedStyle(button);
    }

    @FXML
    private void menuButtonEquipeEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        toEnteredStyle(button);
    }

    @FXML
    private void menuButtonEquipeClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        App.getInstance().EntrarTela(FORM_EQUIPE, content, primmaryStage, blackShadow);
        setActive(button);
    }
    
    
    @FXML
    void menuButtonAuditoriaClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        App.getInstance().EntrarTela(FORM_AUDITORIAS, content, primmaryStage, blackShadow);
        setActive(button);
    }

    @FXML
    void menuButtonAuditoriaEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        toEnteredStyle(button);
    }

    @FXML
    void menuButtonAuditoriaExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        toExitedStyle(button);
    }

    @FXML
    void menuButtonDespesasClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        App.getInstance().EntrarTela(FORM_DESPESAS, content, primmaryStage, blackShadow);
        setActive(button);
    }

    @FXML
    void menuButtonDespesasEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        toEnteredStyle(button);
    }

    @FXML
    void menuButtonDespesasExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        toExitedStyle(button);
    }

    @FXML
    void menuButtonDoacoesClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
        App.getInstance().EntrarTela(FORM_DOACOES, content, primmaryStage, blackShadow);
        setActive(button);
    }

    @FXML
    void menuButtonDoacoesEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        toEnteredStyle(button);
    }

    @FXML
    void menuButtonDoacoesExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        toExitedStyle(button);
    }
   
    private void toExitedStyle(Button button) {
        button.getStyleClass().remove("menuButtonEntered");
        button.getStyleClass().add("menuButtonExited");
    }

    private void toEnteredStyle(Button button) {
        button.getStyleClass().remove("menuButtonExited");
        button.getStyleClass().add("menuButtonEntered");
    }

    private void disableActive() {
        if(menuButtonActive != null) {
            getRectangle(menuButtonActive).setVisible(false);
       }
    }

    private void setActive(Button button) {
        disableActive();
        menuButtonActive = button;
        getRectangle(menuButtonActive).setVisible(true);
    }

    private Rectangle getRectangle(Button button) {
        Pane pane = (Pane) button.getGraphic();
        for(Node node : pane.getChildren()) {
            if(node instanceof Rectangle rectangle) {
                return rectangle;
            }
        }
        return null;
    }     
}
