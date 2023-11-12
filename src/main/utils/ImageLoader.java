/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.utils;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author micha
 */
public class ImageLoader {
    
     public static void CarregarImagem(ImageView imageView, String foto, Rectangle clip){
        Task<Image> loadImageTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return ImageCache.loadImage(foto);
            }
        };

        loadImageTask.setOnSucceeded(event -> {
            Image loadedImage = loadImageTask.getValue();
            imageView.setImage(loadedImage);        
            imageView.setClip(clip);
        });

        loadImageTask.setOnFailed(event -> {
            
        });

        new Thread(loadImageTask).start();
    }
}
