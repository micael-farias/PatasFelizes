package main.utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

public class ImageConverter {

    public static byte[] ConvertUrlToByteArray(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            
            try (InputStream inputStream = connection.getInputStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[]{};
        }
    }
    
    public static byte[] ImageFileToByteArray(String imagePath) {
        File file = new File("C:\\Users\\micha\\Desktop\\Workspace2\\Patas 3.0\\PatasFelizes\\src"+imagePath);
        byte[] imageBytes = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(imageBytes);
        } catch (IOException ex) {
            Logger.getLogger(ImageConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageBytes;
    
    }

}
