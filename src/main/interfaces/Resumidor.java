package main.interfaces;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface Resumidor<T> {
    void onResume(Pane contentFather, Stage primmaryStage, Pane blackShadow, T[] dados);
}
