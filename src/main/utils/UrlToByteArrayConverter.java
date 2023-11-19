package main.utils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlToByteArrayConverter {

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
            return null;
        }
    }
}
