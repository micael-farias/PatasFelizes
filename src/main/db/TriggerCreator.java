package main.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TriggerCreator {


    public static void create(Statement statement) throws SQLException {
        createTriggersForTableAndColumns("Animais", "Nome,Sexo,Castrado,Status", statement, "Nome");

        createTriggersForTableAndColumns("Doacoes", "Doador,Valor,Data", statement, "Doador");

        createTriggersForTableAndColumns("Voluntarios", "Email,Telefone", statement, "Nome");

        createTriggersForTableAndColumns("Despesas", "Valor,Data,Tipo", statement, "Descricao");

        createTriggersForTableAndColumns("Procedimentos", "IdVoluntario,IdAnimal,Data,Tipo", statement, "Descricao");

        createTriggersForTableAndColumns("Adotantes", "Nome,Contato,CEP,Cidade,Rua,Bairro,Numero", statement, "Nome");
    }

    public static void createTriggersForTableAndColumns(String tableName, String columns, Statement statement, String descritor) throws SQLException {
        String[] columnArray = columns.split(",");

        for (String column : columnArray) {
            String triggerName = tableName + "AposAtualizar_" + column.trim();
            String triggerSQL = generateTriggerSQL(triggerName, tableName, column.trim(), descritor);
            System.out.println(triggerSQL+"\n");
            statement.executeUpdate(triggerSQL);
          //  System.out.println("Trigger criada para " + tableName + "." + column);
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
