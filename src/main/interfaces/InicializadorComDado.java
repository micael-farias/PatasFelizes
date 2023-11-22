package main.interfaces;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface InicializadorComDado<T> {
    void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow, T[] dado);
}