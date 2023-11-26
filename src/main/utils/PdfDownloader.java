package main.utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }
}
