package main.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import main.App;
import main.enums.MensagemTipo;
import main.utils.EmailSenderThread;
import main.utils.LeitorSecrets;

public class ImportData {

    public static int importar(File file) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:patas.db");
        Statement statement = connection.createStatement();

        HashMap<Integer, String> falhas = new HashMap<>();
        int counter = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                try {
                    statement.executeUpdate(line.trim());
                } catch (Exception e) {
                    falhas.put(counter++, line);
                    System.err.println("Erro ao executar a instrução SQL: " + line);
                    e.printStackTrace();
                }
            }
        }

        // Fechar recursos
        statement.close();
        connection.close();

        if(counter > 0){
            // Gravar as falhas em um arquivo
           File arquivoFalhas = gravarFalhasEmArquivo(falhas);

           // Enviar e-mail
           String[] dados = LeitorSecrets.lerSecrets();
           var sender = new EmailSenderThread(dados[0], "Dados mal importados", "A importação desses dados não ocorreu de forma bem-sucedida",
             (dado) -> { excluirArquivoFalhas(); });
           sender.setFile(arquivoFalhas);
           sender.start();
           App.getInstance().SetMensagem(MensagemTipo.ERRO, falhas.size() + " tuplas falharam na importação dos dados");
        }
        return counter;
       
    }

    private static File gravarFalhasEmArquivo(HashMap<Integer, String> falhas) throws IOException {
        File arquivoFalhas = new File("falhas.sql");
        arquivoFalhas.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoFalhas))) {
            falhas.forEach((key, value) -> {
                try {
                    writer.write(value);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        
        return arquivoFalhas;
    }

    public static void excluirArquivoFalhas() {
        File arquivoFalhas = new File("falhas.sql");

        if (arquivoFalhas.exists()) {
            if (arquivoFalhas.delete()) {
                System.out.println("Arquivo de falhas excluído com sucesso.");
            } else {
                System.err.println("Falha ao excluir o arquivo de falhas.");
            }
        } else {
            System.out.println("O arquivo de falhas não existe.");
        }
    }
}
