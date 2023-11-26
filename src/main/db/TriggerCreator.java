package main.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TriggerCreator {


    public static void create(Statement statement) throws SQLException {
        // Exemplo de uso para a tabela Animais
        createTriggersForTableAndColumns("Animais", "Nome,Sexo,Castrado,Status", statement, "Nome");

        // Exemplo de uso para a tabela Doacoes
        createTriggersForTableAndColumns("Doacoes", "Doador,Valor,Data", statement, "Doador");

        // Exemplo de uso para a tabela Voluntarios
        createTriggersForTableAndColumns("Voluntarios", "Email,Telefone", statement, "Nome");

        // Exemplo de uso para a tabela Despesas
        createTriggersForTableAndColumns("Despesas", "Valor,Data,Tipo", statement, "Descricao");

        // Exemplo de uso para a tabela Tarefas
        createTriggersForTableAndColumns("Tarefas", "IdVoluntario,IdAnimal,Data,Tipo,Realizado", statement, "Descricao");

        // Exemplo de uso para a tabela Adotantes
        createTriggersForTableAndColumns("Adotantes", "Nome,Contato,CEP,Cidade,Rua,Bairro,Numero", statement, "Nome");
    }

    public static void createTriggersForTableAndColumns(String tableName, String columns, Statement statement, String descritor) throws SQLException {
        String[] columnArray = columns.split(",");

        for (String column : columnArray) {
            String triggerName = tableName + "AposAtualizar_" + column.trim();
            String triggerSQL = generateTriggerSQL(triggerName, tableName, column.trim(), descritor);
            statement.executeUpdate(triggerSQL);
            System.out.println("Trigger criada para " + tableName + "." + column);
        }
    }

    public static String generateTriggerSQL(String triggerName, String tableName, String columnName, String descritor) {
        return String.format("CREATE TRIGGER IF NOT EXISTS %s\n" +
                        "AFTER UPDATE OF %s ON %s\n" +
                        "FOR EACH ROW\n" +
                        "WHEN NEW.%s != OLD.%s\n" +  //
                        "BEGIN\n" +
                        "        INSERT INTO Alteracoes (TabelaAfetada, IdRegistroAfetado, Descritor, ColunaAlterada, ValorAntigo, ValorNovo, DataAlteracao)\n" +
                        "        VALUES ('%s', NEW.Id, OLD."+descritor+",'%s', OLD.%s, NEW.%s, datetime('now', 'localtime') );\n" +
                        "END;",
                triggerName, columnName, tableName, columnName, columnName, tableName, columnName, columnName, columnName);
    }
}
