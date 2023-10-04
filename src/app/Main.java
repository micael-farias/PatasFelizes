package app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/View.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/view.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Patas Felizes");
            primaryStage.getIcons().add(new Image("/images/pata.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}