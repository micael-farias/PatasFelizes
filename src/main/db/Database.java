package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import main.App;
import main.enums.MensagemTipo;
import static main.utils.Constantes.PATH_DATA_BASE;
import main.utils.FileUtils;

public class Database {
    
    private Connection connection = null;
    private static Database instance;
    
    private Database(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:patas.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            String sql = FileUtils.LoadTextFile(PATH_DATA_BASE);
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            //App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao criar banco de dados", null);

        }
    }
    
    public static Database GetInstanceDB(){
        if(instance == null){
            instance = new Database();
        }
        
        return instance;
    }
    
    public Connection GetConnection(){
        return connection;
    }
    
    public void CloseConnection(){
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao fechar conexão com o banco de dados", null);
        }
    }
    
    
    
    
}
