package main.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import main.controllers.BaseController;
import main.controllers.MensagemController;
import main.enums.MensagemTipo;
import main.interfaces.Acao;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComAcao;
import main.interfaces.InicializadorComDado;
import main.interfaces.InicializadorComOrigem;
import main.interfaces.InicializadorComOrigemEDado;
import main.interfaces.InicializadorMiniDialog;
import main.interfaces.Resumidor;
import static main.utils.Constantes.FORM_HOME;
import static main.utils.Constantes.DIALOG_MENSAGEM;

public class InicializarFormulario {
    
    private List<Parent> dialogosAberto = new ArrayList<>();
    private static HashMap<String,FXMLLoadResult> mapping = new HashMap<>();
    private Pane mensagemErro;
    
    public void setPane(Pane mensagem){
        mensagemErro = mensagem;
    }
    
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
            for(Parent dialogoAberto : dialogosAberto){
                if(pane.getChildren().contains(dialogoAberto)){
                    pane.getChildren().remove(dialogoAberto);
                }
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
              controlador.Inicializar(telaOrigem,content,primaryStage, blackShadow, acao, dados);

              AnchorPane pane = (tela.contains("Filtrar")) ? CentralizarDialogoDireita(root2, primaryStage) : CentralizarDialogo(root2, primaryStage);

              if(pane.getChildren().contains(blackShadow)){
                  pane.getChildren().remove(blackShadow);
              }
              if(pane.getChildren().contains(mensagemErro)){
                  pane.getChildren().remove(mensagemErro);
              }
              blackShadow.setVisible(true);
              pane.getChildren().addAll(blackShadow, root2, mensagemErro);
              
              blackShadow.setOnMouseClicked(e -> { FecharDialog(primaryStage, blackShadow);});
              

            dialogosAberto.add(root2);
          } catch(IOException e) {
              e.printStackTrace();
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

            AnchorPane pane = (tela.contains("Filtrar")) ? CentralizarDialogoDireita(root2, primaryStage) : CentralizarDialogo(root2, primaryStage);
            if(pane.getChildren().contains(blackShadow)){
                pane.getChildren().remove(blackShadow);
            }
            
            if(pane.getChildren().contains(mensagemErro)){
                pane.getChildren().remove(mensagemErro);
            }
            
            
            blackShadow.setVisible(true);
            pane.getChildren().addAll(blackShadow, root2, mensagemErro);
            
            dialogosAberto.add(root2);
        } catch(IOException e) {
            e.printStackTrace();
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
            if(pane.getChildren().contains(mensagemErro)){
                pane.getChildren().remove(mensagemErro);
            }
            blackShadow.setVisible(true);
            pane.getChildren().addAll(blackShadow, root2, mensagemErro);
            
            dialogosAberto.add(root2);
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
            if(pane.getChildren().contains(mensagemErro)){
                pane.getChildren().remove(mensagemErro);
            }
            blackShadow.setVisible(true);
            pane.getChildren().addAll(blackShadow, root2, mensagemErro);
            
            dialogosAberto.add(root2);
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
            if(pane.getChildren().contains(mensagemErro)){
                pane.getChildren().remove(mensagemErro);
            }
            
            blackShadow.setVisible(true);
            pane.getChildren().addAll(blackShadow, root2, mensagemErro);
            
            dialogosAberto.add(root2);
        } catch(IOException e) {
            e.printStackTrace();
        }        
    }
    
    
    
    public <T> void SetMensagem(MensagemTipo tipo, String mensagem, Acao acao) {
       try {
           mensagemErro.setVisible(true);
           System.out.println(mensagem);
           Stage dialog = new Stage();
           dialog.initStyle(StageStyle.UNDECORATED);

               FXMLLoader loader = new FXMLLoader(getClass().getResource(DIALOG_MENSAGEM));
               Parent root2 = loader.load();
             //  dado =new FXMLLoadResult<>(root2,loader);
             //  mapping.put(DIALOG_MENSAGEM, dado);
           

           MensagemController cam = loader.getController();
           cam.setAcao(acao);
           cam.setData(tipo, mensagem);


           mensagemErro.getChildren().addAll(root2);
           root2.toFront();

           Timeline timeline = new Timeline();
           KeyFrame startFadeOut = new KeyFrame(Duration.seconds(5), new KeyValue(root2.opacityProperty(), 1.0));
           KeyFrame endFadeOut = new KeyFrame(Duration.seconds(6), new KeyValue(root2.opacityProperty(), 0.0));

           timeline.getKeyFrames().addAll(startFadeOut, endFadeOut);
           timeline.setOnFinished(event -> {
                  mensagemErro.getChildren().removeAll(root2);
                  mensagemErro.setVisible(false);
           });

           // Iniciar a animação
           timeline.play();

       } catch (IOException e) {
           e.printStackTrace();
       }
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
    
    public AnchorPane CentralizarDialogoDireita(Parent root, Stage primaryStage){    
    if(primaryStage != null && primaryStage.getScene() != null && primaryStage.getScene().getRoot() != null){
        AnchorPane pane = (AnchorPane) primaryStage.getScene().getRoot();
        
        double paneWidth = pane.getWidth();
        double paneHeight = pane.getHeight();
        
        double rootWidth = root.prefWidth(-1);
        double rootHeight = root.prefHeight(-1);
        
        // Ajuste as âncoras para centralizar o diálogo à direita
        AnchorPane.setTopAnchor(root, (paneHeight - rootHeight) / 2);
        AnchorPane.setLeftAnchor(root, paneWidth - rootWidth);
        
        return pane;
    }
    return null;
}

    
    public void EntrarTelaInicial(Pane content, Stage primmaryStage, Pane blackShadow){
          EntrarTela(FORM_HOME, content, primmaryStage, blackShadow);
    }  
}
