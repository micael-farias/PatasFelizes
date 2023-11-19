package main.interfaces;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface InicializadorComOrigemEDado<T> {
    void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, String origem, T[] dado);
}

