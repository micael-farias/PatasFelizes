package main.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.*;

import main.model.Voluntario;
import static main.utils.DateHelper.DateToCalendar;

public class VoluntarioRepository extends BaseRepository<Voluntario>{

    public VoluntarioRepository() {
        super(Voluntario.class);
    }
    
    public Voluntario EncontrarVoluntarioPor(String nome){

        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryBuscaPorNome("Voluntarios",nome))) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Voluntario encontrado = mapearVoluntario(resultSet);

                    if (resultSet.next()) {
                        return null;
                    }

                    return encontrado;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Voluntario mapearVoluntario(ResultSet resultSet) throws SQLException {
        Voluntario voluntario = new Voluntario();
        voluntario.setId(resultSet.getInt("Id"));
        voluntario.setNome(resultSet.getString("Nome"));
        voluntario.setFoto(resultSet.getBytes("Foto"));
        voluntario.setEmail(resultSet.getString("Email"));
        voluntario.setTelefone(resultSet.getString("Telefone"));
        voluntario.setDataCadastro(DateToCalendar(resultSet.getTimestamp("DataCadastro")));
        
        return voluntario;
    }
    
    public Voluntario EncontrarVoluntarioPor(int id){
        String sql = "SELECT * FROM Voluntarios WHERE Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearVoluntario(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;    }
    
    public  Set<String> EncontrarNomesVoluntarios(){
        Set<String> lista = new HashSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT NOME FROM VOLUNTARIOS")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while(resultSet.next()) {
                       lista.add(resultSet.getString("NOME"));
                    }

                    return lista;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;      
    }
    
    public List<Voluntario> ObterVoluntarios(){
        List<Voluntario> lista = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM VOLUNTARIOS")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while(resultSet.next()) {
                       Voluntario encontrado = mapearVoluntario(resultSet);
                       lista.add(encontrado);
                    }

                    return lista;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;   
    }

    public List<Voluntario> ObterVoluntariosPorNome(String nome){
        List<Voluntario> lista = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryBuscaPorNome("Voluntarios", nome))) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while(resultSet.next()) {
                       Voluntario encontrado = mapearVoluntario(resultSet);
                       lista.add(encontrado);
                    }

                    return lista;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;      
    }
    public Voluntario inserirVoluntario(Voluntario voluntario) throws SQLException {
        String sql = "INSERT INTO Voluntarios (Nome, Foto, Email, Telefone, DataCadastro) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, voluntario.getNome());
            statement.setBytes(2, voluntario.getFoto());
            statement.setString(3, voluntario.getEmail());
            statement.setString(4, voluntario.getTelefone());
            statement.setTimestamp(5, new Timestamp(voluntario.getDataCadastro().getTimeInMillis()));

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    voluntario.setId(generatedId);
                    return voluntario;
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public Voluntario atualizarVoluntario(Voluntario voluntario) throws SQLException {
        String sql = "UPDATE Voluntarios SET Nome=?, Foto=?, Email=?, Telefone=?, DataCadastro=? WHERE Id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, voluntario.getNome());
            statement.setBytes(2, voluntario.getFoto());
            statement.setString(3, voluntario.getEmail());
            statement.setString(4, voluntario.getTelefone());
            statement.setTimestamp(5, new Timestamp(voluntario.getDataCadastro().getTimeInMillis()));
            statement.setInt(6, voluntario.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return voluntario;
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public Voluntario Salvar(int idVoluntario, String nome, String email, String telefone, byte[] fotoVoluntario) throws SQLException, IllegalAccessException {
       
       Voluntario voluntario;
      
       if(idVoluntario == -1){
            voluntario = new Voluntario();
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);     
            voluntario.setFoto(fotoVoluntario);
            inserirVoluntario(voluntario);  
       }else{
            voluntario = new Voluntario();
            voluntario.setId(idVoluntario);
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);  
            voluntario.setFoto(fotoVoluntario);
            atualizarVoluntario(voluntario);  
       }
       
       return voluntario;
    }
    
}
