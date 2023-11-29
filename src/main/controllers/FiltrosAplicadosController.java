package main.controllers;


import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import main.App;
import main.interfaces.Acao;
import static main.utils.Constantes.FILTRO_APLICADO_VIEW;
import static main.utils.Constantes.FILTRO_VIEW;
import main.views.gridview.GridView;

public class FiltrosAplicadosController {

  
    
  @FXML
    private HBox caixaFiltrados;

    @FXML
    private HBox caixaGeral;

    @FXML
    private HBox caixaOrdenacao;

    @FXML
    private ImageView excluirFiltro;

    @FXML
    private GridPane gridFiltros;

    @FXML
    private HBox ordenacao;

    @FXML
    private ScrollPane scroll;
    
    private FiltroAplicadoController controller;
    private HBox ordenadosBox;
    private HBox filtradosBox;
    
    public void setData(){
        var fxmlLoader = App.getInstance().RealizarLoadFXML(FILTRO_APLICADO_VIEW, HBox.class);
        var children = ordenacao.getChildren();
        children.add(fxmlLoader.getResult());
        controller = fxmlLoader.getLoader().getController();
          scroll.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                event.consume(); // Consume o evento para evitar a rolagem para baixo
            }
        });
    }

    public GridPane getGridFiltros() {
        gridFiltros.getChildren().clear();
        return gridFiltros;
    }
    
    public void setOrdenacao(String ordenacao){
        controller.setData(ordenacao);       
    }
    
    public void removerCaixaOrdenados(){
        if(caixaGeral.getChildren().contains(caixaOrdenacao)){
            ordenadosBox = caixaOrdenacao;
            caixaGeral.getChildren().remove(caixaOrdenacao);
            caixaOrdenacao = ordenadosBox;
            
            scroll.setPrefWidth(USE_COMPUTED_SIZE);
            caixaFiltrados.setPrefWidth(USE_COMPUTED_SIZE);
        }
    } 
    
    public void adicionarCaixaOrdenados(){
        if(!caixaGeral.getChildren().contains(caixaOrdenacao)){
            filtradosBox = caixaFiltrados;
            caixaGeral.getChildren().remove(caixaFiltrados);
            caixaFiltrados = filtradosBox;
            caixaGeral.getChildren().addAll(caixaOrdenacao, caixaFiltrados);
            
            scroll.setPrefWidth(560);
            caixaFiltrados.setPrefWidth(691);
        }
    }
    
    public void removerCaixaFiltrados(){
        if(caixaGeral.getChildren().contains(caixaFiltrados)){
            filtradosBox = caixaFiltrados;
            caixaGeral.getChildren().remove(caixaFiltrados);
            caixaFiltrados = filtradosBox;
        }
    }
    
    public void adicionarCaixaFiltrados(){
        
        if(!caixaGeral.getChildren().contains(caixaFiltrados)){
            ordenadosBox = caixaOrdenacao;
            caixaGeral.getChildren().remove(caixaOrdenacao);
            caixaOrdenacao = ordenadosBox;
            caixaGeral.getChildren().addAll(caixaOrdenacao, caixaFiltrados);
            
            scroll.setPrefWidth(560);
            caixaFiltrados.setPrefWidth(691);
        }
    }
    
    public void excluirFiltro(Acao acao){
        excluirFiltro.setOnMouseClicked(e ->{
            acao.RealizarAcao(new Object[]{});
        });       
    }

    public HBox getCaixaGeral() {
        return caixaGeral;
    }
    

}