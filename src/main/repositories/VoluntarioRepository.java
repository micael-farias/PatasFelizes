package main.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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

                    // Verificar se há mais de uma correspondência
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
        return SelecionarTodos("*", "ID = '"+id+"'", null, Voluntario.class).get(0);
    }
    
    public  Set<String> EncontrarNomesVoluntarios(){
        return new HashSet<>(SelecionarTodos("NOME", null, null, String.class));
    }
    
    public List<Voluntario> ObterVoluntarios(){
        return new ArrayList<>(SelecionarTodos("*", null, "NOME DESC", Voluntario.class));
    }

    public List<Voluntario> ObterVoluntariosPorNome(String nome){
        return new ArrayList<>(SelecionarTodos("*", "NOME LIKE '%"+nome+"%'", "NOME DESC", Voluntario.class));
    }
    
    public void Salvar(int idVoluntario, String nome, String email, String telefone, byte[] fotoVoluntario) throws SQLException, IllegalAccessException {
       
       Voluntario voluntario;
      
       if(idVoluntario == -1){
            voluntario = new Voluntario();
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);     
            voluntario.setFoto(fotoVoluntario);
            this.Inserir(voluntario);  
       }else{
            voluntario = new Voluntario();
            voluntario.setId(idVoluntario);
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);  
            voluntario.setFoto(fotoVoluntario);
            Atualizar(voluntario);  
       }
    }
    
}
