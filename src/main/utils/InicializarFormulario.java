package main.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.controllers.BaseController;
import main.controllers.CadastrarAnimalController;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import static main.utils.Constantes.FORM_HOME;

public class InicializarFormulario {
    
    public <T> FXMLLoadResult<T> RealizarLoadFXML(String arquivo, Class<T> type) {
        try {
            var resource = getClass().getResource(arquivo);
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            T typedRoot = type.cast(root);
            return new FXMLLoadResult<>(typedRoot, loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void EntrarTela(String tela, Pane content, Pane blackShadow){   
        try {
            if(blackShadow.isVisible()) blackShadow.setVisible(false);
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            Inicializador controlador = loader.getController();
            controlador.Inicializar(content, blackShadow);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu);    
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public <T> void EntrarTela(String tela, Pane content, T dado, Pane blackShadow){   
        try {
            if(blackShadow.isVisible()) blackShadow.setVisible(false);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            InicializadorComDado<T> controlador = loader.getController();
            controlador.Inicializar(content, blackShadow, dado);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu);   
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
     public <T> void AbrirDialog(String tela, Pane contentFather, Pane blackShadow){   
        try{
            blackShadow.setVisible(true);
            Stage dialog = new Stage();
            dialog.setOnCloseRequest(event -> {  EntrarTelaInicial(contentFather, blackShadow); });
            dialog.initStyle(StageStyle.UTILITY);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/fxml/CadastrarAnimal.fxml"));
            Parent root2 = loader.load();
            CadastrarAnimalController cam = loader.getController();
            cam.Inicializar(contentFather, blackShadow);
            cam.setStage(dialog);
            Scene scene2 = new Scene(root2);
            dialog.setScene(scene2);  
            dialog.show();
        }catch(IOException e){
         
         
        }        
    }
    
    
    public void EntrarTelaInicial(Pane content, Pane blackShadow){
          EntrarTela(FORM_HOME, content, blackShadow);
    }
            
}
