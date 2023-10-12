package main.utils;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class ImageCache {
    
    private static final Map<String, Image> imageCache = new HashMap<>();

    public static Image loadImage(String imageUrl) {
        if (imageCache.containsKey(imageUrl)) {
            return imageCache.get(imageUrl);
        } else {
            Image image = new Image(imageUrl);
            imageCache.put(imageUrl, image);
            return image;
        }
    }
}