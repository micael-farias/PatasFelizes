package main;

import java.io.IOException;
import java.util.Calendar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.interfaces.InicializadorBase;

import static main.utils.Constantes.FORM_BASE;
import static main.utils.Constantes.PATH_FILES;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Font.loadFont(getClass().getResourceAsStream(PATH_FILES + "NotoSansDevanagari.ttf"), 12);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FORM_BASE));
            Parent root = loader.load();
            InicializadorBase base = loader.getController();
            base.Inicializar(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/main/views/css/view.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Patas Felizes");
            primaryStage.getIcons().add(new Image("/assets/images/pata.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }   
}