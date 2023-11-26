package main.controllers;


import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.db.ImportData;
import main.interfaces.Inicializador;
import static main.utils.Constantes.FORM_HOME;
import static main.utils.Constantes.PATH_FILES;
import static main.utils.Constantes.PATH_IMAGES;
import static main.utils.ImageLoader.CarregarSql;

public class ImportarBancoController implements Inicializador {

    @FXML
    private Button cancelar;

    @FXML
    private Button confirmar;

    @FXML
    private ImageView iconNuvem;

    @FXML
    private HBox layoutAdicionarComprovante;
    private File banco;
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow){
        layoutAdicionarComprovante.setOnMouseClicked(e ->{
            File arquivoSql = CarregarSql(primmaryStage);
            if(arquivoSql != null){
                var mg = PATH_IMAGES+"arquivo-pdf.png";
                iconNuvem.setImage(new Image(mg));
                banco = arquivoSql;
            }
        });
        
        cancelar.setOnMouseClicked(e ->{
            App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });
        
        
        confirmar.setOnMouseClicked(e ->{
            if(banco == null) return;
            
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setPrefSize(100, 100);
            layoutAdicionarComprovante.getChildren().add(progressIndicator);

            ImportData.importar(banco);

            layoutAdicionarComprovante.getChildren().remove(progressIndicator);
            App.getInstance().EntrarTelaOnResume(FORM_HOME, contentFather, primmaryStage, blackShadow, null);
        });
        
        
    
    }

    @Override
    public void Inicializar(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
        setListeners(contentFather, primmaryStage, blackShadow);
    }

}
