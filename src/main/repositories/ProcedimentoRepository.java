package main.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.db.Database;
import main.model.Animal;
import main.model.Despesa;

import main.model.Procedimento;
import main.model.Tarefa;
import main.model.Voluntario;
import static main.utils.DateHelper.DateToCalendar;


public class ProcedimentoRepository extends BaseRepository<Procedimento>{

    public ProcedimentoRepository() {
        super(Procedimento.class);
    }


    public Procedimento Salvar(int idProcedimento, String descricao, Calendar data, String tipo, Despesa despesa, Voluntario voluntario, Animal animal, boolean realizado) throws SQLException {
        Procedimento procedimento = new Procedimento();
        procedimento.setId(idProcedimento);
        procedimento.setDescricao(descricao);
        procedimento.setData(data);
        procedimento.setTipo(tipo);
        procedimento.setVoluntario(voluntario);
        procedimento.setDespesa(despesa);
        procedimento.setAnimal(animal);
        procedimento.setRealizado(realizado);
        procedimento.setDespesa(despesa);

        if (idProcedimento == -1) {
            return inserirProcedimento(procedimento);
        } else {
            return atualizarProcedimento(procedimento);
        }
    }

    private Procedimento inserirProcedimento(Procedimento procedimento) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Procedimentos (descricao, data, tipo, idDespesa, idVoluntario, idAnimal, dataCadastro, realizado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, procedimento.getDescricao());
            statement.setTimestamp(2, new Timestamp(procedimento.getData().getTimeInMillis()));
            statement.setString(3, procedimento.getTipo());
            statement.setObject(4, procedimento.getDespesa() != null ? procedimento.getDespesa().getId() : null);
            statement.setObject(5, procedimento.getVoluntario() != null ? procedimento.getVoluntario().getId() : null);
            statement.setObject(6, procedimento.getAnimal() != null ? procedimento.getAnimal().getId() : null);
            statement.setTimestamp(7, new Timestamp(procedimento.getDataCadastro().getTimeInMillis()));
            statement.setBoolean(8, procedimento.isRealizado());
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    procedimento.setId(generatedId);
                    return procedimento;
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    private Procedimento atualizarProcedimento(Procedimento procedimento) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Procedimentos SET descricao=?, data=?, tipo=?, idDespesa=?, idVoluntario=?,idAnimal=?, realizado=? WHERE id=?")) {

            statement.setString(1, procedimento.getDescricao());
            statement.setTimestamp(2, new Timestamp(procedimento.getData().getTimeInMillis()));
            statement.setString(3, procedimento.getTipo());
            statement.setObject(4, procedimento.getDespesa() != null ? procedimento.getDespesa().getId() : null);
            statement.setObject(5, procedimento.getVoluntario() != null ? procedimento.getVoluntario().getId() : null);
            statement.setObject(6, procedimento.getAnimal() != null ? procedimento.getAnimal().getId() : null);
            statement.setBoolean(7, procedimento.isRealizado());
            statement.setInt(8, procedimento.getId());
                                System.out.println("SQL gerado: " + statement.toString());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return procedimento;
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public List<Procedimento> encontrarProcedimentosPor(int idAnimal){
        List<Procedimento> procedimentosAnimal = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Procedimentos WHERE idAnimal=?")) {

            statement.setInt(1, idAnimal);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    procedimentosAnimal.add(mapearProcedimento(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return procedimentosAnimal;
    }

    public Procedimento encontrarProcedimentosPorId(int id)  {
        try (PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Procedimentos WHERE id=?")) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearProcedimento(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Procedimento encontrarProcedimentosPorTarefa(int idProcedimento) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Procedimentos WHERE id=?")) {

            statement.setInt(1, idProcedimento);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearProcedimento(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set<String> encontrarNomesTipos() {
        Set<String> tiposNomes = new HashSet<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT tipo FROM Procedimentos")) {

            while (resultSet.next()) {
                tiposNomes.add(resultSet.getString("tipo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposNomes;
    }

    public Procedimento encontrarProcedimentosPorDespesa(int idDespesa) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Procedimentos WHERE IdDespesa=?")) {

            statement.setInt(1, idDespesa);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearProcedimento(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Procedimento mapearProcedimento(ResultSet resultSet) throws SQLException {
        Procedimento procedimento = new Procedimento();
        procedimento.setId(resultSet.getInt("id"));
        procedimento.setDescricao(resultSet.getString("descricao"));        
        procedimento.setData(DateToCalendar(resultSet.getDate("data")));
        procedimento.setTipo(resultSet.getString("tipo"));
        procedimento.setRealizado(resultSet.getBoolean("realizado"));
          // Mapear Despesa
        int despesaId = resultSet.getInt("idDespesa");
        if (despesaId > 0) {
            Despesa despesa = new DespesaRepository().EncontrarDespesaPor(despesaId);
            procedimento.setDespesa(despesa);
        }

        // Mapear Voluntario
        int voluntarioId = resultSet.getInt("idVoluntario");
        if (voluntarioId > 0) {
            Voluntario voluntario = new VoluntarioRepository().EncontrarVoluntarioPor(voluntarioId);
            procedimento.setVoluntario(voluntario);
        }

        // Mapear Animal
        int animalId = resultSet.getInt("idAnimal");
        if (animalId > 0) {
            Animal animal = new AnimalRepository().EncontrarAnimalPor(animalId);
            procedimento.setAnimal(animal);
        }
            // Você precisará implementar métodos para carregar Despesa, Voluntario, Tarefa e Animal a partir de seus respectivos IDs

        return procedimento;
    }

    public List<Procedimento> encontrarProcedimentosPor(String descricao, int idAnimal) {
        List<Procedimento> procedimentosAnimal = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Procedimentos WHERE DESCRICAO LIKE '%"+descricao+"%' AND IDANIMAL = " + idAnimal)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    procedimentosAnimal.add(mapearProcedimento(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return procedimentosAnimal;
    }
}
