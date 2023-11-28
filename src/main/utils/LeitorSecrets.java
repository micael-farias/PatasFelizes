package main.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import main.App;
import main.enums.MensagemTipo;

public class LeitorSecrets {

    public static String[] lerSecrets() {
        String[] secrets = new String[2];

        String diretorioUsuario = System.getProperty("user.home");

        Path caminhoArquivo = Path.of(diretorioUsuario, "PatasFelizes", "Secrets", "secrets.txt");
        try{
            try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo.toFile()))) {
                secrets[0] = converterBase64ParaString(reader.readLine());
                secrets[1] = converterBase64ParaString(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao encontrar o arquivo de secrets");
        }    
        return secrets;
    }

    private static String converterBase64ParaString(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
