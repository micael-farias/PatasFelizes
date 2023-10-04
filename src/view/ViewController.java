package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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

    private Button createButtonPet() {
        Button button = new Button();
        button.getStyleClass().add("buttonPet");
        VBox vBox = new VBox();
        vBox.getStyleClass().add("vBoxPet");
        ImageView imagePet = new ImageView(new Image("/images/gato.jpg", 182, 176, false, true));
        HBox hBoxInfoPetExternal = new HBox();
        hBoxInfoPetExternal.getStyleClass().add("hBoxInfoPetExternal");
        HBox hBoxInfoPetInternal = new HBox();
        hBoxInfoPetInternal.getStyleClass().add("hBoxInfoPetInternal");
        Label labelName = new Label("Nome");
        labelName.getStyleClass().add("nameButtonPet");
        Circle circle = new Circle(2);
        circle.getStyleClass().add("pointButtonPet");
        Label months = new Label("48 meses");
        months.getStyleClass().add("monthButtonPet");
        hBoxInfoPetInternal.getChildren().addAll(labelName, circle, months);
        ImageView sexPet = new ImageView(new Image("/images/femea.png", 24, 24, true, true));
        hBoxInfoPetExternal.getChildren().addAll(hBoxInfoPetInternal, sexPet);
        vBox.getChildren().addAll(imagePet, hBoxInfoPetExternal);
        button.setGraphic(vBox);
        return button;
    }

    @FXML
    private void newPetClicked(MouseEvent event) {
        int size = vBoxPets.getChildren().size();
        HBox hBox = (HBox) vBoxPets.getChildren().get(size - 1);
        if(hBox.getChildren().size() < 5) {
            Button newButton = createButtonPet();
            hBox.getChildren().add(newButton);
        } else {
                anchorPanePet.setMinHeight(anchorPanePet.getHeight() + 32 + 224);
                HBox newHBox = new HBox();
                newHBox.getStyleClass().add("hBoxScrollPet");
                Button newButton = createButtonPet();
                newHBox.getChildren().add(newButton);
                vBoxPets.getChildren().add(newHBox);
        }
    }
    
}
