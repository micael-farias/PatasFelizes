package main.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    
    public static String LoadTextFile(String filename){
        try {
            String srcPath = new File("src").getAbsolutePath();
            filename = srcPath + File.separator + filename;
            long time = System.currentTimeMillis();
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            StringBuilder sb = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null ){
                sb.append(line).append("\n");
            }

            reader.close();
            time = System.currentTimeMillis() - time;
            System.out.println("O arquivo foi lido em " + time + " ms");
            return sb.toString();
       
        }catch(IOException e){
            return null;
        }
    }
}
