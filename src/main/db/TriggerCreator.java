package main.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TriggerCreator {

    private static final String URL = "jdbc:sqlite:/path/to/your/database.db";

    public static void create(Statement statement) throws SQLException {
        // Exemplo de uso para a tabela Animais
        createTriggersForTableAndColumns("Animais", "Nome,DataNascimento,Descricao,Sexo,Castrado,Status", statement);

        // Exemplo de uso para a tabela Doacoes
        createTriggersForTableAndColumns("Doacoes", "Doador,Valor,Data", statement);

        // Exemplo de uso para a tabela Voluntarios
        createTriggersForTableAndColumns("Voluntarios", "Nome,Email,Telefone", statement);

        // Exemplo de uso para a tabela Despesas
        createTriggersForTableAndColumns("Despesas", "Descricao,Valor,Data,Tipo,Realizada", statement);

        // Exemplo de uso para a tabela Tarefas
        createTriggersForTableAndColumns("Tarefas", "IdVoluntario,IdAnimal,Descricao,Data,Tipo,Realizado", statement);

        // Exemplo de uso para a tabela Procedimentos
        createTriggersForTableAndColumns("Procedimentos", "Descricao,IdAnimal,Data,Tipo,IdVoluntario,IdDespesa,IdTarefa,Realizado", statement);

        // Exemplo de uso para a tabela Adotantes
        createTriggersForTableAndColumns("Adotantes", "Nome,Contato,CEP,Cidade,Rua,Bairro,Numero", statement);
    }

    public static void createTriggersForTableAndColumns(String tableName, String columns, Statement statement) throws SQLException {
        String[] columnArray = columns.split(",");

        for (String column : columnArray) {
            String triggerName = tableName + "AposAtualizar_" + column.trim();
            String triggerSQL = generateTriggerSQL(triggerName, tableName, column.trim());
            statement.executeUpdate(triggerSQL);
            System.out.println("Trigger criada para " + tableName + "." + column);
        }
    }

    public static String generateTriggerSQL(String triggerName, String tableName, String columnName) {
        return String.format("CREATE TRIGGER IF NOT EXISTS %s\n" +
                        "AFTER UPDATE OF %s ON %s\n" +
                        "FOR EACH ROW\n" +
                        "WHEN NEW.%s != OLD.%s\n" +  // Condição para verificar se a coluna foi alterada
                        "BEGIN\n" +
                        "        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)\n" +
                        "        VALUES ('%s', NEW.Id, '%s', OLD.%s, NEW.%s, datetime('now', 'localtime') );\n" +
                        "END;",
                triggerName, columnName, tableName, columnName, columnName, tableName, columnName, columnName, columnName);
    }
}
