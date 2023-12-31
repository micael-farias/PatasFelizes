package main.db;

import java.io.*;
import java.sql.*;
import main.App;
import main.enums.MensagemTipo;
import main.utils.ArquivoUtil;

public class ExportData {

    public static void export() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:patas.db");
            DatabaseMetaData metaData = connection.getMetaData();

            FileWriter fileWriter = new FileWriter("patas.sql");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            String[] tables = {"Voluntarios","Adotantes","Adocoes","Animais","Despesas","Doacoes","Procedimentos"};

            for (String tableName : tables) {

                printWriter.println("DELETE FROM " + tableName + ";");

                Statement statement = connection.createStatement();
                String query = "SELECT * FROM " + tableName;
                ResultSet resultSet = statement.executeQuery(query);

                ResultSetMetaData rsMetaData = resultSet.getMetaData();
                int columnCount = rsMetaData.getColumnCount();

                while (resultSet.next()) {
                    printWriter.print("INSERT INTO " + tableName + " VALUES (");

                    for (int i = 1; i <= columnCount; i++) {
                        if (rsMetaData.getColumnType(i) == Types.BLOB) {

                            InputStream inputStream = resultSet.getBinaryStream(i);
                            if (inputStream != null) {
                                byte[] bytes = inputStream.readAllBytes();
                                printWriter.print("X'" + bytesToHex(bytes) + "'");
                            } else {
                                printWriter.print("NULL");
                            }
                        } else {
                            
                            String stringValue = escapeString(resultSet.getString(i));
                            printWriter.print("'" + stringValue + "'");
                        }

                        if (i < columnCount) {
                            printWriter.print(", ");
                        }
                    }

                    printWriter.println(");");
                }

                // Fechar recursos da tabela atual
                resultSet.close();
                statement.close();
            }

            // Fechar recursos
            printWriter.close();
            fileWriter.close();
            connection.close();
            ArquivoUtil.salvarArquivoNoDiretorioUsuario("patas.sql", new File("patas.sql"));

            System.out.println("Dados de todas as tabelas exportados como instruções SQL para patas.sql.");

        } catch (Exception e) {
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao exportar os dados", null);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
    
    private static String escapeString(String input) {
        if(input != null)
            return input.replace("'", "''");
        return "";
    }
}
