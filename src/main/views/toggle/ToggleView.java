package main.views.toggle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.App;
import main.controllers.ToogleButtonController;
import main.utils.ToogleEnum;

public class ToggleView {
    
   ToogleButtonController controlador;
   EventHandler<MouseEvent> clickDireita;
   EventHandler<MouseEvent> clickEsquerda;
   String path;
   
   public ToggleView(String path){
       this.path = path;
   }
    
    public void CriarToggle(Pane contentFather, EventHandler<MouseEvent> clickDireita, EventHandler<MouseEvent> clickEsquerda) {
       var fxmlLoader = App.getInstance().RealizarLoadFXML(path, HBox.class);
       controlador = fxmlLoader.getLoader().getController();
       controlador.setListeneres(clickDireita, clickEsquerda);
       ObservableList<Node> children = contentFather.getChildren();
       children.add(fxmlLoader.getResult());
       ativarBotao(ToogleEnum.ESQUERDO);
    }    
    
   public void ativarBotao(ToogleEnum opcao){
       controlador.ativarBotao(opcao);
   }
   
   public void ativarToogle(){
       controlador.ativarToogle();
   }
   
   public void desativarToogle(){
       controlador.desativarToogle();
   }
   
   public void setImagemDireita(String imagem){
        controlador.setImagemDireita(imagem);
   }
    
   public void setImagemEsquerda(String imagem){
        controlador.setImagemEsquerda(imagem);
   }
   
   public void setTextoDireito(String texto){
        controlador.setTextoDireito(texto);
   }
    
   public void setTextoEsquerdo(String texto){
        controlador.setTextoEsquerdo(texto);
   }
   
   public ToogleEnum getSelectedItem(){
       return controlador.getSelectedItem(); 
   }
}
