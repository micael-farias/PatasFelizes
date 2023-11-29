package main.utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.App;
import main.enums.MensagemTipo;

public class PdfDownloader {

    public static void baixarPdf(byte[] conteudoPdf, String nomeArquivo) {
        try {
            // Obter o diretório de downloads do usuário

                // Criar um documento PDF
                Document document = new Document();
                String caminhoDestino = System.getProperty("user.home") + "/Downloads/"+nomeArquivo;
                PdfWriter.getInstance(document, new FileOutputStream(caminhoDestino));
                document.open();

                // Adicionar a imagem ao documento
                Image imagem = Image.getInstance((conteudoPdf));
                document.add(imagem);

                document.close();
                
                App.getInstance().SetMensagem(MensagemTipo.SUCESSO, "Um PDF foi salvo na pasta Downloads \nClique aqui para abrir",(dados) -> {
                   abrirExplorer(caminhoDestino);
                  });
                

        } catch (Exception e) {
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao enviar o PDF", null);

        }
    }
    
       private static void abrirExplorer(String caminho) {
        // Verifica se o Desktop é suportado (pode não ser suportado em alguns ambientes)
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            File arquivo = new File(caminho);

            try {
                // Abre o explorador de arquivos na localização especificada
                desktop.open(arquivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Desktop não suportado
            System.out.println("Desktop não suportado. Não é possível abrir o explorador de arquivos.");
        }
    }
}


