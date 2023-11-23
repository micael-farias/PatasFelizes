package main.utils;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import static main.utils.Rectangles.CreateBlackImage;

public class ImageCache {
    
    private static final Map<String, ImageData> imageCacheBytes = new HashMap<>();

     public static Image loadImageByte(byte[] imagebytes, String idFoto) {
        if(imagebytes == null){
           return CreateBlackImage();
        }
         
        if (imageCacheBytes.containsKey(idFoto)) {
            ImageData data = imageCacheBytes.get(idFoto);
            if(data.getTamanhoEmBytes() != imagebytes.length){
                return Load(imagebytes, idFoto);
            }
            return data.getImagem();
        } else {
            return Load(imagebytes, idFoto);
        }
    } 
     
    public static Image Load(byte[] imageBytes, String idFoto){
        Image image = ImageLoader.loadImageFromBytes(imageBytes);
        ImageData imageData = new ImageData(image, imageBytes.length);
        imageCacheBytes.put(idFoto, imageData);
        return image;
    }
}