package main.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import main.controllers.BaseController;
import main.interfaces.Acao;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComAcao;
import main.interfaces.InicializadorComDado;
import main.interfaces.InicializadorComOrigem;
import main.interfaces.InicializadorComOrigemEDado;
import main.interfaces.InicializadorMiniDialog;
import main.interfaces.Resumidor;
import static main.utils.Constantes.FORM_HOME;

public class InicializarFormulario {
    
    private Parent dialogoAberto;
    private static HashMap<String,FXMLLoadResult> mapping = new HashMap<>();
    
    public boolean MappingContatis(String key){
        return mapping.containsKey(key);
    }
    
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
        if(primmaryStage != null && primmaryStage.getScene() != null && primmaryStage.getScene().getRoot() != null){
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
            mapping.put(tela, new FXMLLoadResult<Pane>(menu,loader));
       } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public void EntrarTelaComRemocao(String tela, String telaRemovida, Pane content, Stage primmaryStage, Pane blackShadow){   
        try {          
            mapping.remove(telaRemovida);
            removerDialogoAberto(blackShadow, primmaryStage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            Inicializador controlador = loader.getController();
            controlador.Inicializar(content, primmaryStage, blackShadow);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu); 
            mapping.put(tela, new FXMLLoadResult<Pane>(menu,loader));
       } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    
    public <T> void EntrarTela(String tela, Pane content, Stage primmaryStage, T[] dado, Pane blackShadow){   
        try {
            removerDialogoAberto(blackShadow, primmaryStage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Pane menu = loader.load();
            InicializadorComDado<T> controlador = loader.getController();
            controlador.Inicializar(content,primmaryStage, blackShadow, dado);
            content.getChildren().clear();
            ObservableList<Node> children = content.getChildren();
            children.addAll(menu);
            mapping.put(tela, new FXMLLoadResult<Pane>(menu,loader));
       } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    public <T> void EntrarTelaOnResume(String tela, Pane content, Stage primmaryStage, Pane blackShadow, T[] dados){   
        removerDialogoAberto(blackShadow, primmaryStage);
        FXMLLoadResult<Pane> result = mapping.get(tela);
        Resumidor<T> controlador = result.getLoader().getController();
        controlador.onResume(content,primmaryStage, blackShadow, dados);
        content.getChildren().clear();
        ObservableList<Node> children = content.getChildren();
        children.addAll(result.getResult());     
    }
    
    public <T> void AbrirDialogComAcao(String tela, String telaOrigem, Pane content, Stage primaryStage, Pane blackShadow, Object[] dados, Acao acao){                
        try {

              Stage dialog = new Stage();
              dialog.initStyle(StageStyle.UNDECORATED);
              dialog.initModality(Modality.APPLICATION_MODAL);
              dialog.initOwner(primaryStage);

              FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
              Parent root2 = loader.load();
              InicializadorComAcao controlador = loader.getController();
              controlador.Inicializar(telaOrigem, content,primaryStage, blackShadow, acao, dados);

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
    
    public <T> void EntrarTelaNoAction(String tela, Pane content, Stage primmaryStage, Pane blackShadow){   
        removerDialogoAberto(blackShadow, primmaryStage);
        FXMLLoadResult<Pane> result = mapping.get(tela);
        content.getChildren().clear();
        ObservableList<Node> children = content.getChildren();
        children.addAll(result.getResult());     
    }
    public <T> void EntrarTelaNoActionComRemocao(String tela, String telaRemovida, Pane content, Stage primmaryStage, Pane blackShadow){  
        mapping.remove(telaRemovida);
        removerDialogoAberto(blackShadow, primmaryStage);
        FXMLLoadResult<Pane> result = mapping.get(tela);
        content.getChildren().clear();
        ObservableList<Node> children = content.getChildren();
        children.addAll(result.getResult());     
    }    
    
    public <T> void FecharDialog(Stage primmaryStage, Pane blackShadow){   
        removerDialogoAberto(blackShadow, primmaryStage);
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
    
    public <T> void AbrirDialogComDado(String tela, Pane contentFather, Stage primaryStage, Pane blackShadow, T[] dado) {   
        try {

            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);

            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Parent root2 = loader.load();
            InicializadorComDado cam = loader.getController();
            cam.Inicializar(contentFather, primaryStage, blackShadow, dado);

            AnchorPane pane = CentralizarDialogo(root2, primaryStage);
            
            if(pane.getChildren().contains(blackShadow)){
                pane.getChildren().remove(blackShadow);
            }
            
            blackShadow.setVisible(true);
            pane.getChildren().addAll(blackShadow, root2);
            
            dialogoAberto = root2;
        } catch(IOException e) {
            e.printStackTrace();
        }        
    }
    
      public <T> void AbrirDialogComOrigem(String telaDestino, String telaOrigem, Pane contentFather, Stage primaryStage, Pane blackShadow) {   
        try {

            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(telaDestino));
            Parent root2 = loader.load();
            InicializadorComOrigem cam = loader.getController();                      
            cam.Inicializar(contentFather, primaryStage, blackShadow, telaOrigem);

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
    
    public <T> void AbrirDialogComOrigemEDado(String telaDestino, String telaOrigem, Pane contentFather, Stage primaryStage, Pane blackShadow, T[] dado) {   
        try {
            Stage dialog = new Stage();
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(telaDestino));
           
            Parent root2 = loader.load();
            InicializadorComOrigemEDado cam = loader.getController();                      
            cam.Inicializar(contentFather, primaryStage, blackShadow, telaOrigem, dado);

            AnchorPane pane = CentralizarDialogo(root2, primaryStage);
            
            if(pane.getChildren().contains(blackShadow)){
                pane.getChildren().remove(blackShadow);
            }
            
            blackShadow.setVisible(true);
            pane.getChildren().addAll(blackShadow, root2);
            
            dialogoAberto = root2;
        } catch(IOException e) {
            e.printStackTrace();
        }        
    }
    
    public <T> void AbrirDialogAlinhado(String tela, Pane contentFather, Parent reference, Pane blackShadow) {   
    try {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UNDECORATED);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
        Parent root2 = loader.load();
        Inicializador cam = loader.getController();
        cam.Inicializar(contentFather, (Stage) contentFather.getScene().getWindow(), blackShadow);

        AnchorPane pane = (AnchorPane) contentFather.getScene().getRoot();

        double topOffset = reference.getBoundsInParent().getMaxY();
        double leftOffset = reference.getBoundsInParent().getMaxX()-calcularDistanciaDireita(reference);

        AnchorPane.setTopAnchor(root2, topOffset);
        AnchorPane.setLeftAnchor(root2, leftOffset);

        pane.getChildren().addAll(root2);

        // Configurar a posição do dialog
        dialog.setY(contentFather.getScene().getWindow().getY() + topOffset);
        dialog.setX(contentFather.getScene().getWindow().getX() + leftOffset);
        
        dialogoAberto = root2;
    } catch(IOException e) {
        e.printStackTrace();
    }        
}
     public  double calcularDistanciaDireita(Node node) {
        // Obtém a Scene associada à Node
        Window janela = node.getScene().getWindow();

        // Obtém as coordenadas locais da Node
        Bounds boundsInLocal = node.getBoundsInLocal();

        // Converte as coordenadas locais para as coordenadas da tela
        Bounds boundsInScreen = node.localToScreen(boundsInLocal);

        // Calcula a distância da extremidade direita da Node até a extremidade direita da janela
        double distanciaDireita = janela.getX() + janela.getWidth() - boundsInScreen.getMaxX();

        return distanciaDireita;
    }
    
    public AnchorPane CentralizarDialogo(Parent root, Stage primmaryStage){    
        if(primmaryStage != null && primmaryStage.getScene() != null && primmaryStage.getScene().getRoot() != null){
            AnchorPane pane = (AnchorPane) primmaryStage.getScene().getRoot();
            AnchorPane.setTopAnchor(root, (pane.getHeight() - root.prefHeight(-1)) / 2);
            AnchorPane.setLeftAnchor(root, (pane.getWidth() - root.prefWidth(-1)) / 2);
            return pane;
        }
        return null;
    }
    
    public void EntrarTelaInicial(Pane content, Stage primmaryStage, Pane blackShadow){
          EntrarTela(FORM_HOME, content, primmaryStage, blackShadow);
    }
            
}
