package main.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Animal;
import static main.utils.DateHelper.DateToCalendar;
import static main.utils.ImageConverter.ImageFileToByteArray;

public class AnimalRepository extends BaseRepository<Animal>{
    
    public AnimalRepository(){
        super(Animal.class);                        
    }
    
    public ArrayList<Animal> EncontrarAnimais() {
        String sql = "SELECT * FROM Animais ORDER BY DataCadastro DESC";
        ArrayList<Animal> animais = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Animal animal = mapearAnimal(resultSet);
                animais.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animais;
    }
    
    public Animal Salvar(int idAnimal, String nomeAnimal, Calendar dataNascimentoAnimal, String descricaoAnimal, char sexoAnimal, boolean castrado, byte[] fotoAnimal, String status) throws SQLException, IllegalAccessException {
        Animal animal;
        fotoAnimal = (fotoAnimal == null) ?  ImageFileToByteArray("pet.png") : fotoAnimal;
        
        if(idAnimal == -1){
            animal = new Animal();
            animal.setNome(nomeAnimal);
            animal.setDataNascimento(dataNascimentoAnimal);
            animal.setDescricao(descricaoAnimal);
            animal.setCastrado(castrado);
            animal.setFoto(fotoAnimal);
            animal.setStatus(status);           
            animal.setSexo(sexoAnimal);
            inserirAnimal(animal);          
        }else{
            animal = EncontrarAnimalPor(idAnimal);
            animal.setNome(nomeAnimal);
            animal.setDataNascimento(dataNascimentoAnimal);
            animal.setDescricao(descricaoAnimal);
            animal.setCastrado(castrado);
            animal.setFoto(fotoAnimal == null ? animal.getFoto() : fotoAnimal);
            animal.setStatus(status);     
            animal.setSexo(sexoAnimal);
            atualizarAnimal(animal);
        }   
        
        return animal;
    }
    private Animal inserirAnimal(Animal animal) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Animais (Nome, DataNascimento, Foto, Descricao, Sexo, Castrado, Status, DataCadastro) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {

        statement.setString(1, animal.getNome());
        statement.setTimestamp(2, animal.getDataNascimento() != null ? new Timestamp(animal.getDataNascimento().getTimeInMillis())  : null);
        statement.setBytes(3, animal.getFoto());
        statement.setString(4, animal.getDescricao());
        statement.setString(5, String.valueOf(animal.getSexo()));
        statement.setBoolean(6, animal.isCastrado());
        statement.setString(7, animal.getStatus());
        statement.setTimestamp(8, new Timestamp(animal.getDataCadastro().getTimeInMillis()));
        System.out.println(statement);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                animal.setId(generatedId);
                return animal;
            }
        }
    } catch (SQLException e) {
        throw e;
    }

    return null;
}

private Animal atualizarAnimal(Animal animal) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(
            "UPDATE Animais SET Nome=?, DataNascimento=?, Foto=?, Descricao=?, Sexo=?, Castrado=?, Status=? " +
                    "WHERE Id=?")) {

        statement.setString(1, animal.getNome());
        statement.setTimestamp(2, animal.getDataNascimento() == null ? null : new Timestamp(animal.getDataNascimento().getTimeInMillis()));
        statement.setBytes(3, animal.getFoto());
        statement.setString(4, animal.getDescricao());
        statement.setString(5, String.valueOf(animal.getSexo()));
        statement.setBoolean(6, animal.isCastrado());
        statement.setString(7, animal.getStatus());
        statement.setInt(8, animal.getId());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            return animal;
        }
    } catch (SQLException e) {
        throw e;
    }

    return null;
}



    
     public Animal EncontrarAnimalPor(int id) {
        String sql = "SELECT * FROM Animais WHERE Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearAnimal(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Animal mapearAnimal(ResultSet resultSet) throws SQLException {
        Animal animal = new Animal();
        animal.setId(resultSet.getInt("Id"));
        animal.setNome(resultSet.getString("Nome"));
        animal.setDataNascimento(DateToCalendar(resultSet.getTimestamp("DataNascimento")));
        animal.setFoto(resultSet.getBytes("Foto"));
        animal.setDescricao(resultSet.getString("Descricao"));
        animal.setSexo(resultSet.getString("Sexo").charAt(0));
        animal.setCastrado(resultSet.getBoolean("Castrado"));
        animal.setStatus(resultSet.getString("Status"));
        animal.setDataCadastro(DateToCalendar(resultSet.getTimestamp("DataCadastro")));

        return animal;
    }
    
    public Animal EncontrarAnimalPorNome(String nome) {    
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryBuscaPorNome("Animais",nome))) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Animal encontrado = mapearAnimal(resultSet);

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
    
    public List<Animal> EncontrarAnimaisPorNome(String nome) {  
        List<Animal> lista = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryBuscaPorNome("Animais",nome))) {
          System.err.println(QueryBuscaPorNome("Animais",nome));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while(resultSet.next()) {
                       Animal encontrado = mapearAnimal(resultSet);
                       lista.add(encontrado);
                    }

                    return lista;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set<String> EncontrarNomesAnimais() {     
        Set<String> set = new HashSet<String>();
        String sql = "SELECT NOME FROM Animais";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                set.add(resultSet.getString("NOME"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return set;

    }

    public void AtualizarStatusAnimal(String status, int idAnimal) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Animais SET status=? WHERE id=?")) {

            statement.setString(1, status);
            statement.setInt(2, idAnimal);
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected != 1) {
                throw new Exception("Falha na atualização do Status do animal");
            }
        } catch (SQLException e) {
            throw e;
        }

    }
    
    public StringBuilder  filtrarPorSexo( boolean filtrarMasculino, boolean filtrarFeminino, boolean sexoDesconhecido){
        StringBuilder sql = new StringBuilder("");
        if(filtrarMasculino && filtrarFeminino && sexoDesconhecido) return sql;
        boolean algumFiltroAtivado = filtrarMasculino || filtrarFeminino || sexoDesconhecido;

        if (algumFiltroAtivado) {
            sql.append(" AND (");

            if (filtrarMasculino) {
                sql.append("Sexo = 'M' OR ");
            }

            if (filtrarFeminino) {
                sql.append("Sexo = 'F' OR ");
            }

            if (sexoDesconhecido) {
                sql.append("Sexo = 'N' OR ");
            }

            sql.delete(sql.length() - 4, sql.length());

            sql.append(")");
        }
        
        return sql;
    }

    
     public List<Animal> selecionarAnimais(String ordenacao, String status,
            boolean filtrarMasculino, boolean filtrarFeminino, boolean sexoDesconhecido,
            boolean filtrarSim, boolean filtrarNao,
            Calendar intervaloPrimeiro, Calendar intervaloSegundo) throws SQLException {
            List<Animal> animais = new ArrayList<>();

        try {

            StringBuilder sql = new StringBuilder("SELECT * FROM Animais WHERE 1 = 1");

            if (status != null && !status.isEmpty()) {
                sql.append(" AND Status = '").append(status).append("'");
            }          
            
            sql.append(filtrarPorSexo(filtrarMasculino, filtrarFeminino, sexoDesconhecido));
            
            if (filtrarSim) {
                if(!filtrarNao){
                    sql.append(" AND Castrado = 1");
                } 
            }else if(filtrarNao){
                sql.append(" AND CASTRADO = 0");
            }
            
            
            if (intervaloPrimeiro != null && intervaloSegundo != null) {
                sql.append(" AND DataNascimento BETWEEN ? AND ?");
            }
            
            if (ordenacao != null && !ordenacao.isEmpty()) {
                sql.append(" ORDER BY ").append(ordenacao);
            }
            
            System.out.println(sql);
            
            try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                        
                if(intervaloPrimeiro != null && intervaloSegundo != null){
                    statement.setTimestamp(1, new Timestamp(intervaloPrimeiro.getTimeInMillis()));
                    statement.setTimestamp(2, new Timestamp(intervaloSegundo.getTimeInMillis()));                        
                }
       
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Animal animal = mapearAnimal(resultSet);
                        animais.add(animal);
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return animais;
    }
    
    
}
