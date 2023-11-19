/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author micha
 */
public class ImageLoader {
    
      public static void CarregarImagem(ImageView imageView, byte[] bytesFotoAnimal, String idFoto, Rectangle clip){
        Task<Image> loadImageTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return ImageCache.loadImageByte(bytesFotoAnimal,idFoto);
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
   
    public static byte[] CarregarImagemLocal(Stage primaryStage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                return LoadImageBytes(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return null;
    }
    
    private static byte[] LoadImageBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toByteArray();
        }
    }
    
    public static Image loadImageFromBytes(byte[] imageBytes) {
       ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
       Image image = new Image(inputStream);
       return image;
   }
}
