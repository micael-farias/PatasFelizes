package main.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewController implements Initializable {
    
    private Button menuButtonActive;
    @FXML
    private Button menuButtonPets;
    @FXML
    private Button menuButtonFinancas;
    @FXML
    private Button menuButtonAgenda;
    @FXML
    private Button menuButtonEquipe;
    @FXML
    private VBox vBoxPets;
    @FXML
    private AnchorPane anchorPanePet;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setActive(menuButtonPets);
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
            // ...
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
        setActive(button);
        
    }

    @FXML
    private void menuButtonFinancasExited(MouseEvent event) {
        Button button = (Button) event.getSource();
        toExitedStyle(button);
    }

    @FXML
    private void menuButtonFinancasEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        toEnteredStyle(button);
    }

    @FXML
    private void menuButtonFinancasClicked(MouseEvent event) {
        Button button = (Button) event.getSource();
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
        setActive(button);
    }

    @FXML
    private void newPetClicked(MouseEvent event) {
        
    }
    
}
