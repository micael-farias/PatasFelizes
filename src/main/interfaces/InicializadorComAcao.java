package main.interfaces;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface InicializadorComAcao {
    void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao, Object[] dados);
}

