
package main.views.textfield;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.interfaces.Acao;
import static main.utils.Constantes.PATH_IMAGES;

public class CheckBoxCustomized {
    public boolean Checked = false;
    
    public void inicializar(ImageView check){
        setImage(Checked, check);
        check.setOnMouseClicked(event -> {
            Checked = !Checked;
            setImage(Checked, check);
            System.out.println(Checked);
            if (Checked) {  
            }else{
            }
            
        }); 
    }
    
    public boolean getChecked(){
        return Checked;
    }
    
    public void setImage(boolean realizado, ImageView checkBox){
        Checked = realizado;
        if(realizado){
            checkBox.setImage(new Image(PATH_IMAGES + "check_azul_checked.png"));          
        }else{
            checkBox.setImage(new Image(PATH_IMAGES + "check_azul_not_checked.png"));           
        }       
    }
}
