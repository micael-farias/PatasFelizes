package main.views.toggle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.controllers.ToogleButtonController;
import static main.utils.Constantes.TOOGLE_BUTTON;
import main.utils.InicializarFormulario;
import main.utils.ToogleEnum;

public class ToggleView {
    
   ToogleButtonController controlador;
    
   public void CriarToogle(Pane contentFather){
        InicializarFormulario inicializar = new InicializarFormulario();
        var fxmlLoader = inicializar.RealizarLoadFXML(TOOGLE_BUTTON, HBox.class);
        controlador = fxmlLoader.getLoader().getController();
        controlador.setListeneres();
        ObservableList<Node> children = contentFather.getChildren();
        children.add(fxmlLoader.getResult());          
   }
    
   public void onClickImagemDireita(EventHandler<MouseEvent> click){
        controlador.onClickImagemDireita(click);
   }
       
   public void onClickImagemEsquerda(EventHandler<MouseEvent> click){
        controlador.onClickImagemEsquerda(click);
   }     
    
   public void ativarBotao(ToogleEnum opcao){
        controlador.ativarBotao(opcao);
   }
}
