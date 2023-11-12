package main.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.controllers.BaseController;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComDado;
import static main.utils.Constantes.FORM_HOME;

public class InicializarFormulario {
    
    private Parent dialogoAberto;
    
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
    
    public void removerDialogoAberto(Pane blackShadow, Stage primmaryStage){    
        if(blackShadow.isVisible()) blackShadow.setVisible(false);
        if(primmaryStage.getScene() != null && primmaryStage.getScene().getRoot() != null){
            AnchorPane pane = (AnchorPane) primmaryStage.getScene().getRoot();
            if(pane.getChildren().contains(dialogoAberto)){
                pane.getChildren().remove(dialogoAberto);
            }
        }
    }
    
    public void EntrarTela(String tela, Pane content, Stage primmaryStage, Pane blackShadow){   
        try {          
            removerDialogoAberto(blackShadow, primmaryStage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            Inicializador controlador = loader.getController();
            controlador.Inicializar(content, primmaryStage, blackShadow);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu);    
       } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public <T> void EntrarTela(String tela, Pane content, Stage primmaryStage, T dado, Pane blackShadow){   
        try {
            removerDialogoAberto(blackShadow, primmaryStage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            InicializadorComDado<T> controlador = loader.getController();
            controlador.Inicializar(content,primmaryStage, blackShadow, dado);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu);   
       } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public <T> void AbrirDialog(String tela, Pane contentFather, Stage primaryStage, Pane blackShadow) {   
        try {

            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Parent root2 = loader.load();
            Inicializador cam = loader.getController();
            cam.Inicializar(contentFather, primaryStage, blackShadow);

            AnchorPane pane = CentralizarDialogo(root2, primaryStage);
            
            if(pane.getChildren().contains(blackShadow)){
                pane.getChildren().remove(blackShadow);
            }
            
            blackShadow.setVisible(true);
            pane.getChildren().addAll(blackShadow, root2);
            
            dialogoAberto = root2;
        } catch(IOException e) {
        }        
    }
    
    public AnchorPane CentralizarDialogo(Parent root, Stage primaryStage){    
        AnchorPane pane = (AnchorPane) primaryStage.getScene().getRoot();
        AnchorPane.setTopAnchor(root, (pane.getHeight() - root.prefHeight(-1)) / 2);
        AnchorPane.setLeftAnchor(root, (pane.getWidth() - root.prefWidth(-1)) / 2);
        return pane;
    }
    
    public void EntrarTelaInicial(Pane content, Stage primmaryStage, Pane blackShadow){
          EntrarTela(FORM_HOME, content, primmaryStage, blackShadow);
    }
            
}
