package main.interfaces;

import javafx.scene.layout.Pane;

public interface InicializadorComDado<T> {
    void Inicializar(Pane contentFather, T dado);
}