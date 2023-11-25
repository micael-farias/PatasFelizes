package main.db;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import static main.utils.Constantes.PATH_FILES;

public class ImportData {

    public static void importar() {
        try {
            // Conectar ao banco de dados SQLite
            Connection connection = DriverManager.getConnection("jdbc:sqlite:patas.db");
            Statement statement = connection.createStatement();

            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/micha/Desktop/Workspace2/Patas 3.0/PatasFelizes/src/assets/files/patas.sql"));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                try {
                    // Tenta executar a instrução SQL
                    statement.executeUpdate(line.trim());
                } catch (Exception e) {
                    // Se ocorrer um erro, imprime o erro, mas continua para a próxima instrução
                    System.err.println("Erro ao executar a instrução SQL: " + line);
                    e.printStackTrace();
                }
            }

            // Fechar recursos
            bufferedReader.close();
            statement.close();
            connection.close();

            System.out.println("Importação de dados concluída.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
