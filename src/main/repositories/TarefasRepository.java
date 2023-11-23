package main.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.db.Database;
import main.model.Animal;
import main.model.Tarefa;
import main.model.Voluntario;
import static main.utils.DateHelper.DateToCalendar;

public class TarefasRepository extends BaseRepository<Tarefa>{

    public TarefasRepository() {
        super(Tarefa.class);
    }

    // Método para salvar uma tarefa no banco de dados
    public Tarefa Salvar(int idTarefa, Voluntario voluntario, Animal animal, String descricao, Calendar data, String tipo, boolean realizado) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(idTarefa);
        tarefa.setVoluntario(voluntario);
        tarefa.setAnimal(animal);
        tarefa.setDescricao(descricao);
        tarefa.setData(data);
        tarefa.setTipo(tipo);
        tarefa.setRealizado(realizado);

        if (idTarefa == -1) {
            return inserirTarefa(tarefa);
        } else {
            return atualizarTarefa(tarefa);
        }
    }

    // Método para inserir uma tarefa no banco de dados
    private Tarefa inserirTarefa(Tarefa tarefa) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Tarefas (idVoluntario, idAnimal, descricao, data, tipo, realizado, dataCadastro) VALUES (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, tarefa.getVoluntario() != null ? tarefa.getVoluntario().getId() : null);
            statement.setObject(2, tarefa.getAnimal() != null ? tarefa.getAnimal().getId() : null);
            statement.setString(3, tarefa.getDescricao());
            statement.setTimestamp(4, new Timestamp(tarefa.getData().getTimeInMillis()));
            statement.setString(5, tarefa.getTipo());
            statement.setBoolean(6, tarefa.isRealizada());
            statement.setTimestamp(7, new Timestamp(tarefa.getDataCadastro().getTimeInMillis()));

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    tarefa.setId(generatedId);
                    return tarefa;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Método para atualizar uma tarefa no banco de dados
    private Tarefa atualizarTarefa(Tarefa tarefa) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Tarefas SET idVoluntario=?, idAnimal=?, descricao=?, data=?, tipo=?, realizado=? WHERE id=?")) {

            statement.setObject(1, tarefa.getVoluntario() != null ? tarefa.getVoluntario().getId() : null);
            statement.setObject(2, tarefa.getAnimal() != null ? tarefa.getAnimal().getId() : null);
            statement.setString(3, tarefa.getDescricao());
            statement.setTimestamp(4, new Timestamp(tarefa.getData().getTimeInMillis()));
            statement.setString(5, tarefa.getTipo());
            statement.setBoolean(6, tarefa.isRealizada());
            statement.setInt(7, tarefa.getId());
                                System.out.println("SQL gerado: " + statement.toString());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return tarefa;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Método para encontrar uma tarefa por ID no banco de dados
    public Tarefa EncontrarTarefaPor(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Tarefas WHERE id=?")) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearTarefa(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Método para obter todas as tarefas do banco de dados
    public List<Tarefa> ObterTarefas() {
        List<Tarefa> tarefasRetornadas = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM Tarefas")) {

            while (resultSet.next()) {
                Tarefa tarefa = mapearTarefa(resultSet);
                tarefasRetornadas.add(tarefa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(tarefasRetornadas, Comparator.comparing(Tarefa::getData).reversed());
        return tarefasRetornadas;
    }

    // Método para mapear uma tarefa a partir de um ResultSet
    private Tarefa mapearTarefa(ResultSet resultSet) throws SQLException {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(resultSet.getInt("id"));
        tarefa.setDescricao(resultSet.getString("descricao"));
        tarefa.setData(DateToCalendar(resultSet.getDate("data")));
        tarefa.setTipo(resultSet.getString("tipo"));
        tarefa.setRealizado(resultSet.getBoolean("realizado"));

      // Mapear Voluntario
        int voluntarioId = resultSet.getInt("idVoluntario");
        if (voluntarioId > 0) {
            Voluntario voluntario = new VoluntarioRepository().EncontrarVoluntarioPor(voluntarioId);
            tarefa.setVoluntario(voluntario);
        }
        // Mapear Animal
        int animalId = resultSet.getInt("idAnimal");
        if (animalId > 0) {
            Animal animal = new AnimalRepository().EncontrarAnimalPor(animalId);
            tarefa.setAnimal(animal);
        }

        return tarefa;
    }

    public Set<String> obterNomesTiposTarefa() {
        Set<String> tiposNomes = new HashSet<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT tipo FROM Tarefas")) {

            while (resultSet.next()) {
                tiposNomes.add(resultSet.getString("tipo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposNomes;    
    }

    public List<Tarefa> EncontrarTarefasPorDescricao(String tarefa) {
        List<Tarefa> tarefasRetornadas = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Tarefas Where descricao like '%"+tarefa+"%'")) {

            while (resultSet.next()) {
                Tarefa t = mapearTarefa(resultSet);
                tarefasRetornadas.add(t);      
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefasRetornadas;  
    }
}
