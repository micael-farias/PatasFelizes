package main.utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ArquivoUtil {

    public static void salvarArquivoNoDiretorioUsuario(String nomeArquivo, File arquivo) {
        String diretorioUsuario = System.getProperty("user.home");
        String caminhoDiretorio = diretorioUsuario + File.separator + "PatasFelizes" + File.separator + "Backups";

        Path pathDiretorio = Path.of(caminhoDiretorio);
        if (!Files.exists(pathDiretorio)) {
            try {
                Files.createDirectories(pathDiretorio);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path pathArquivo = Path.of(caminhoDiretorio, nomeArquivo);

        try {
            Files.copy(arquivo.toPath(), pathArquivo, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Arquivo salvo em: " + pathArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
