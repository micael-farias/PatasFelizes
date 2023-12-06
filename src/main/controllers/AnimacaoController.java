/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.App;
import main.interfaces.Inicializador;

/**
 * FXML Controller class
 *
 * @author micha
 */
public class AnimacaoController implements Inicializador {

    @FXML
    private ImageView imageView;

    @FXML
    private StackPane pa;
    
    @FXML
    private Pane pane;

    @FXML
    private Label label;
    
    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        pane.setOnMouseClicked(e ->{ App.getInstance().FecharDialog(primmaryStage, blackShadow); });
        ParallelTransition parallelTransition = new ParallelTransition();

        // Adiciona a animação de escala
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), imageView);
        scaleTransition.setByX(0.5); // Aumenta horizontalmente pela metade
        scaleTransition.setByY(0.5); // Aumenta verticalmente pela metade
        scaleTransition.setCycleCount(1); // Executa apenas uma vez
        scaleTransition.setAutoReverse(true); // Inverte a animação (aumenta e diminui)

        // Adiciona a animação de rotação
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), imageView);
        rotateTransition.setByAngle(360); // Gira 360 graus (uma volta completa)
        rotateTransition.setCycleCount(1); // Executa apenas uma vez

        parallelTransition.getChildren().addAll(scaleTransition, rotateTransition);

        // Adiciona a animação de escala contínua após a animação simultânea (tempo 3 em diante)
        ScaleTransition continuousScaleTransition = new ScaleTransition(Duration.seconds(2), imageView);
        continuousScaleTransition.setByX(0.5); // Aumenta horizontalmente pela metade
        continuousScaleTransition.setByY(0.5); // Aumenta verticalmente pela metade
        continuousScaleTransition.setCycleCount(Timeline.INDEFINITE); // Repete indefinidamente
        continuousScaleTransition.setAutoReverse(true); // Inverte a animação (aumenta e diminui)

        // Adiciona a Label com transição de opacidade no tempo 3
        label.setOpacity(0);
        continuousScaleTransition.setOnFinished(event -> {
            

        });

        // Combina as animações
        parallelTransition.setOnFinished(event -> {
            Timeline timeline = new Timeline();
           KeyFrame startFadeOut = new KeyFrame(Duration.seconds(2), new KeyValue(label.opacityProperty(), 1.0));
           KeyFrame endFadeOut = new KeyFrame(Duration.ZERO, new KeyValue(label.opacityProperty(), 0.0));

           timeline.getKeyFrames().addAll(startFadeOut, endFadeOut);
           timeline.setOnFinished(e -> {
                System.out.print("terminou");
           });

           // Iniciar a animação
           timeline.play();
           continuousScaleTransition.play(); // Inicia a animação de escala contínua após a animação simultânea
           label.setVisible(true);
        });

        // Inicia a animação simultânea
        parallelTransition.play();
    }
    
}
