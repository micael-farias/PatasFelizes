package main.repositories;


import java.sql.*;
import java.util.Calendar;
import main.db.Database;
import main.model.Adotante;
import static main.utils.DateHelper.DateToCalendar;

public class AdotantesRepository extends BaseRepository<Adotante> {

    public AdotantesRepository() {
        super(Adotante.class);
    }

    public Adotante SalvarAdotante(int idAdotante, String nome, String contato, String cep, String cidade, String rua, String bairro, String numero, String complemento) throws SQLException {
        Adotante adotante = new Adotante();
        adotante.setId(idAdotante);
        adotante.setNome(nome);
        adotante.setContato(contato);
        adotante.setCep(cep);
        adotante.setCidade(cidade);
        adotante.setRua(rua);
        adotante.setBairro(bairro);
        adotante.setNumero(numero);
        adotante.setComplemento(complemento);
        adotante.setDataCadastro(Calendar.getInstance());

        if (idAdotante == -1) {
            return InserirAdotante(adotante);
        } else {
            return AtualizarAdotante(adotante);
        }
    }

    // Method to insert a new Adotante into the database
    public Adotante InserirAdotante(Adotante adotante) throws SQLException {
        String sql = "INSERT INTO ADOTANTES (Nome, Contato, CEP, Cidade, Rua, Bairro, Numero, DataCadastro, Complemento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, adotante.getNome());
            preparedStatement.setString(2, adotante.getContato());
            preparedStatement.setString(3, adotante.getCep());
            preparedStatement.setString(4, adotante.getCidade());
            preparedStatement.setString(5, adotante.getRua());
            preparedStatement.setString(6, adotante.getBairro());
            preparedStatement.setString(7, adotante.getNumero());
            preparedStatement.setTimestamp(8, new Timestamp(adotante.getDataCadastro().getTimeInMillis()));
            preparedStatement.setString(9, adotante.getComplemento());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    adotante.setId(generatedId);
                    return adotante;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return null;
    }

    // Method to update an existing Adotante in the database
    public Adotante AtualizarAdotante(Adotante adotante) throws SQLException {
        String sql = "UPDATE ADOTANTES SET Nome=?, Contato=?, CEP=?, Cidade=?, Rua=?, Bairro=?, Numero=?, DataCadastro=?, Complemento = ? WHERE Id=?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, adotante.getNome());
            preparedStatement.setString(2, adotante.getContato());
            preparedStatement.setString(3, adotante.getCep());
            preparedStatement.setString(4, adotante.getCidade());
            preparedStatement.setString(5, adotante.getRua());
            preparedStatement.setString(6, adotante.getBairro());
            preparedStatement.setString(7, adotante.getNumero());
            preparedStatement.setTimestamp(8, new Timestamp(adotante.getDataCadastro().getTimeInMillis()));
            preparedStatement.setString(9, adotante.getComplemento());
            preparedStatement.setInt(10, adotante.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return adotante;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }
    public Adotante BuscarAdotantePorId(int id) throws SQLException {
        Adotante adotante = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ADOTANTES WHERE ID = ?")) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    adotante = mapearAdotante(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw  e;
        }

        return adotante;
    }

    private Adotante mapearAdotante(ResultSet resultSet) throws SQLException {
        Adotante adotante = new Adotante();
        adotante.setId(resultSet.getInt("Id"));
        adotante.setNome(resultSet.getString("Nome"));
        adotante.setContato(resultSet.getString("Contato"));
        adotante.setCep(resultSet.getString("CEP"));
        adotante.setCidade(resultSet.getString("Cidade"));
        adotante.setRua(resultSet.getString("Rua"));
        adotante.setBairro(resultSet.getString("Bairro"));
        adotante.setNumero(resultSet.getString("Numero"));
        adotante.setComplemento(resultSet.getString("Complemento"));
        adotante.setDataCadastro(DateToCalendar(resultSet.getTimestamp("DataCadastro")));

        return adotante;
    }


}
