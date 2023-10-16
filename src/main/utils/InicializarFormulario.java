package main.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import main.controllers.BaseController;
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
    
    public void EntrarTela(String tela, Pane content){   
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            Inicializador controlador = loader.getController();
            controlador.Inicializar(content);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu);    
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public <T> void EntrarTela(String tela, Pane content, T dado){   
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            InicializadorComDado<T> controlador = loader.getController();
            controlador.Inicializar(content, dado);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu);   
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public void EntrarTelaInicial(Pane content){
          EntrarTela(FORM_HOME, content);
    }
            
}
