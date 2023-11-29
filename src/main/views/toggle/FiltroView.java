package main.views.toggle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.App;
import main.controllers.FiltrosAplicadosController;
import main.controllers.ToogleButtonController;
import main.interfaces.Acao;
import static main.utils.Constantes.FILTRO_VIEW;
import main.utils.ToogleEnum;
import main.views.gridview.GridView;

public class FiltroView {
    
   FiltrosAplicadosController controller; 
   EventHandler<MouseEvent> clickDireita;
   EventHandler<MouseEvent> clickEsquerda;
   
    public void Criar(Pane contentFather) {
       var fxmlLoader = App.getInstance().RealizarLoadFXML(FILTRO_VIEW, AnchorPane.class);
       ObservableList<Node> children = contentFather.getChildren();
       children.add(fxmlLoader.getResult());
       controller = fxmlLoader.getLoader().getController();
       controller.setData();
    }    
    
    public GridPane getGridFiltros(){
        return controller.getGridFiltros();
    }

    public HBox getCaixaGeral(){
        return controller.getCaixaGeral();
    }

    public void setOrdenacao(String ordenacaoSelecionada) {
       controller.setOrdenacao(ordenacaoSelecionada);
    }
       
    public void removerOrdenacao() {
       controller.removerCaixaOrdenados();
    } 

    public void removerFiltros() {
       controller.removerCaixaFiltrados();
    }
    
      public void adicionarCaixaOrdenados(){
        controller.adicionarCaixaOrdenados();
    } 
    
    public void adicionarCaixaFiltrados(){
        controller.adicionarCaixaFiltrados();
    }
    
     public void excluirFiltro(Acao acao){
         controller.excluirFiltro(acao);
     }
  
}
