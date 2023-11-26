package main;

import java.io.IOException;
import java.util.Calendar;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.db.Database;
import main.db.ExportData;
import main.db.ImportData;
import main.interfaces.InicializadorBase;

import static main.utils.Constantes.FORM_BASE;
import static main.utils.Constantes.PATH_FILES;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Database.GetInstanceDB();
            ImportData.importar();
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
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // Adicione aqui a lógica que você deseja executar antes de fechar a janela
                System.out.println("Fechando a aplicação...");

                // Pode impedir o fechamento, se necessário
                // event.consume();
            }
        });
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }   
}