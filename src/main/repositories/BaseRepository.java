package main.repositories;

import main.db.Database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.util.Calendar;

import main.annotations.TableName;

public class BaseRepository<T> {

    protected Connection connection;
    private Class<T> classe;

    public BaseRepository(Class<T> classe){
        this.classe = classe;
        this.connection = Database.GetInstanceDB().GetConnection(); 
    }
    
    public void BeginTransaction() throws SQLException{
        connection.setAutoCommit(false);
    }

    public void CommitTransaction() throws SQLException{
        connection.commit();
        connection.setAutoCommit(true);

    }
    
    public void RollbackTransaction() throws SQLException{
        if (connection != null) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }
    
    public String QueryBuscaPorNome(String tabela, String valor){
       return String.format("SELECT * FROM %s WHERE UPPER(NOME) = UPPER('%s') OR UPPER(NOME) LIKE UPPER('%s%%') OR UPPER(NOME) LIKE UPPER('%%%s%%')",
                tabela, valor, valor, valor);
    }
        
    public String QueryBuscaPorDescricao(String tabela, String valor){
       return String.format("SELECT * FROM %s WHERE UPPER(DESCRICAO) = UPPER('%s') OR UPPER(DESCRICAO) LIKE UPPER('%s%%') OR UPPER(DESCRICAO) LIKE UPPER('%%%s%%')",
                tabela, valor, valor, valor);
    }
  
    public void Excluir(int id) throws  Exception {
        String tabela = ObterNomeTabela(classe);
        String query = "DELETE FROM " + tabela + " WHERE ID = " + id;
        try(Statement statement = connection.createStatement()){
            int rowsAffetected = statement.executeUpdate(query);
            if(rowsAffetected != 1){
                throw new Exception("Deleção ocorreu de forma inesperada");
                        
            }
        }catch(SQLException e){
            throw e;
        }
    }

    private String ObterNomeTabela(Class<?> classe) {
        TableName annotation = classe.getAnnotation(TableName.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return classe.getSimpleName() + "s";
        }
    }
}
