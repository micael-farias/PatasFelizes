package main.interfaces;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface InicializadorDialog {
    void Inicializar(Pane contentFather, Pane blackShadow);
    void setStage(Stage stage);
}

