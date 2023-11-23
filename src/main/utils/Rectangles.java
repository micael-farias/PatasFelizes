package main.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static main.utils.Constantes.PATH_IMAGES;

public class Rectangles {
  
    public static Rectangle GetRectangleImageAnimais(){
        Rectangle clip = new Rectangle(232, 232);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        return clip;
    }
    
    public static Rectangle GetRectanglePaneAnimais(){
        Rectangle clip = new Rectangle(232, 234);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        return clip;
    }
    
    public static Image CreateBlackImage() {
        return new Image(PATH_IMAGES +"black.png");
    }
}
