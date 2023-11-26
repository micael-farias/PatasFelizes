package main.repositories;

import main.db.Database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    
    public int Inserir(T objeto) throws SQLException, IllegalAccessException {
        String tabela = ObterNomeTabela(objeto.getClass());
        Field[] campos = objeto.getClass().getDeclaredFields();

        StringBuilder query = new StringBuilder("INSERT INTO " + tabela + " (");
        StringBuilder values = new StringBuilder(" VALUES (");

        for (Field campo : campos) {
            campo.setAccessible(true);
            if (campo.getName().equals("Id")) {
                continue;
            }
            query.append(campo.getName()).append(", ");
            values.append("?, ");
        }

        query.delete(query.length() - 2, query.length());
        values.delete(values.length() - 2, values.length());
        query.append(")").append(values).append(")");

        return executarQueryInsercao(objeto, query.toString());
    }

    public void Atualizar(T objeto) throws SQLException, IllegalAccessException {
        String tabela = ObterNomeTabela(objeto.getClass());
        Field[] campos = objeto.getClass().getDeclaredFields();
        StringBuilder query = new StringBuilder("UPDATE " + tabela + " SET ");

        for (Field campo : campos) {
            campo.setAccessible(true);
            if (campo.getName().equals("Id") || campo.getName().equals("DataCadastro")) {
                continue;
            }
            query.append(campo.getName()).append("=?, ");
        }

        query.delete(query.length() - 2, query.length());

        query.append(" WHERE ");
        for (Field campo : campos) {
            campo.setAccessible(true);
            if (campo.getName().equals("Id")) {
                query.append(campo.getName()).append("=?");
                break;
            }
        }

        executarQueryAtualizacao(objeto, query.toString());
    }

    public void Excluir(Class<T> objeto, int id) throws  Exception {
        String tabela = ObterNomeTabela(objeto);
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

    public <Tipo> List<Tipo> SelecionarTodos(String colunas, String where, String order, Class<Tipo> tipoResultado) {
        List<Tipo> resultados = new ArrayList<>();
        String tabela = ObterNomeTabela(classe);
        String query = "SELECT " + colunas + " FROM " + tabela;
        if (where != null) query += " WHERE " + where;
        if (order != null) query += " ORDER BY " + order;
        
        try {
            resultados = executarQuerySelecao(tipoResultado, query);
        } catch (Exception ex) {
            Logger.getLogger(BaseRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultados;
    }

    
    public int Count(){
        String tabela = ObterNomeTabela(classe);
        String query = "SELECT COUNT(*) AS count FROM " + tabela;

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return 0;
    }
    
    private String ObterNomeTabela(Class<?> classe) {
        TableName annotation = classe.getAnnotation(TableName.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            // Se a anotação TableName não estiver presente, use o nome da classe como padrão
            return classe.getSimpleName() + "s";
        }
    }

    private int executarQueryInsercao(T objeto, String query) throws SQLException, IllegalAccessException {
        int idGerado;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preencherValoresStatement(objeto, preparedStatement);
            System.out.println("SQL gerado: " + preparedStatement.toString());
            preparedStatement.executeUpdate();
             // Recupera as chaves geradas (IDs) após a execução do insert
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1); // O 1 indica a primeira coluna, que é o ID
                } else {
                    throw new SQLException("Falha ao recuperar o ID após a inserção.");
                }
            }
        }

        return idGerado;
    }
    
    private void executarQueryAtualizacao(T objeto, String query) throws SQLException, IllegalAccessException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            IndiceAtualizacao indice = preencherValoresStatement(objeto, preparedStatement);
            preparedStatement.setInt(indice.posicao, indice.id);
            preparedStatement.executeUpdate();
        }
    }

    private <Tipo> List<Tipo> executarQuerySelecao(Class<Tipo> classe, String query) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        List<Tipo> resultados = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             
            ResultSet resultSet = preparedStatement.executeQuery()) {

            
            while (resultSet.next()) {
                Tipo objeto = classe.getDeclaredConstructor().newInstance();
                Field[] campos = classe.getDeclaredFields();

                if(classe == String.class){
                    resultados.add((Tipo) resultSet.getString(1)); 
                    continue;
                }
                
                for (Field campo : campos) {
                    campo.setAccessible(true);
                    String nome = campo.getName();
                    var coluna = resultSet.findColumn(nome);
                    int tipoColuna = resultSet.getMetaData().getColumnType(coluna);
                    var type = campo.getType();
                    if(type == boolean.class) tipoColuna = java.sql.Types.BOOLEAN;
                    else if(type == double.class) tipoColuna = java.sql.Types.DOUBLE;

                    switch (tipoColuna) {
                        case java.sql.Types.CHAR:
                            String valor = resultSet.getString(nome);
                            if (valor != null && !valor.isEmpty()) {
                               campo.set(objeto, valor.charAt(0));
                            }
                            break;
                        case java.sql.Types.INTEGER:
                            campo.set(objeto, resultSet.getInt(nome));
                            break;
                        case java.sql.Types.DOUBLE:
                            campo.set(objeto, resultSet.getDouble(nome));
                            break;
                        case java.sql.Types.VARCHAR:
                            campo.set(objeto, resultSet.getString(nome));
                            break;
                        case java.sql.Types.TIMESTAMP:
                            Timestamp timestamp = resultSet.getTimestamp(nome);
                            if (timestamp != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(timestamp.getTime());
                                campo.set(objeto, calendar);
                            } else {
                                campo.set(objeto, null);
                            }
                            break;                         
                        case java.sql.Types.BOOLEAN:
                            campo.set(objeto, resultSet.getBoolean(nome));
                            break;
                        case java.sql.Types.BLOB:
                            campo.set(objeto, resultSet.getBytes(nome));
                            break;
                    }
                }

                resultados.add(objeto);
            }
        }
        return resultados;
    }
    
    private IndiceAtualizacao preencherValoresStatement(T objeto, PreparedStatement preparedStatement) throws IllegalAccessException, SQLException {
        Field[] campos = objeto.getClass().getDeclaredFields();
        //query = (java.lang.StringBuilder) "INSERT INTO Despesas (Descricao, Valor, Data, DataCadastro, Tipo, Realizada) VALUES (?, ?, ?, ?, ?, ?)"
        IndiceAtualizacao indiceAtualizacao = new IndiceAtualizacao();
        int indice = 0;
        int proxIndice = 0;
        
        for (Field campo : campos) {
            campo.setAccessible(true);
            Object valorCampo = campo.get(objeto);
            
            if(campo.getName().equals("Id")){                   
                indiceAtualizacao.id = (int) valorCampo;
                indiceAtualizacao.posicao = (int) campos.length - 1;
                continue;
            }
            
            proxIndice = ++indice;
            if (campo.getType() == String.class) {
                preparedStatement.setString(proxIndice, (String) valorCampo);
            } else if (campo.getType() == char.class) {
                preparedStatement.setString(proxIndice,  valorCampo.toString());
            } else if (campo.getType() == int.class || campo.getType() == Integer.class) {
                preparedStatement.setInt(proxIndice, (int) valorCampo);
            } else if (campo.getType() == double.class || campo.getType() == Double.class) {
                preparedStatement.setDouble(proxIndice, (double) valorCampo);
            }  else if (campo.getType() == Calendar.class) {
                Calendar calendar = (Calendar) valorCampo;
                Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
                preparedStatement.setTimestamp(proxIndice, timestamp);
            } else if (campo.getType() == byte[].class) {
                preparedStatement.setBytes(proxIndice, (byte[]) valorCampo);
            }else if (campo.getType() == boolean.class) {
                preparedStatement.setBoolean(proxIndice, (boolean) valorCampo);
            }
        }
        
        return indiceAtualizacao;
    }
    
    private class IndiceAtualizacao{
        int posicao;
        int id;      
    }
}
