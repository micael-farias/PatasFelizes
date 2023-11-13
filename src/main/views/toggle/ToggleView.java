package main.views.toggle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.App;
import main.controllers.ToogleButtonController;
import static main.utils.Constantes.TOOGLE_BUTTON;
import main.utils.ToogleEnum;

public class ToggleView {
    
   ToogleButtonController controlador;
   EventHandler<MouseEvent> clickDireita;
   EventHandler<MouseEvent> clickEsquerda;
    
    public void CriarToggle(Pane contentFather, EventHandler<MouseEvent> clickDireita, EventHandler<MouseEvent> clickEsquerda) {
       var fxmlLoader = App.getInstance().RealizarLoadFXML(TOOGLE_BUTTON, HBox.class);
       controlador = fxmlLoader.getLoader().getController();
       controlador.setListeneres(clickDireita, clickEsquerda);
       ObservableList<Node> children = contentFather.getChildren();
       children.add(fxmlLoader.getResult());
    }    
    
   public void ativarBotao(ToogleEnum opcao){
        controlador.ativarBotao(opcao);
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
}
