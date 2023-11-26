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
import main.model.Procedimento;
import main.model.Tarefa;
import main.model.Voluntario;
import static main.utils.DateHelper.DateToCalendar;

public class TarefasRepository extends BaseRepository<Tarefa>{

    public TarefasRepository() {
        super(Tarefa.class);
    }


    // Método para obter todas as tarefas do banco de dados
    public List<Procedimento> ObterTarefas() {
        List<Procedimento> tarefasRetornadas = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM PROCEDIMENTOS ORDER BY DATACADASTRO DESC")) {

            while (resultSet.next()) {
                Procedimento tarefa = mapearTarefa(resultSet);
                tarefasRetornadas.add(tarefa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefasRetornadas;
    }

    // Método para mapear uma tarefa a partir de um ResultSet
    private Procedimento mapearTarefa(ResultSet resultSet) throws SQLException {
        Procedimento tarefa = new Procedimento();
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
             ResultSet resultSet = statement.executeQuery("SELECT tipo FROM PROCEDIMENTOS")) {

            while (resultSet.next()) {
                tiposNomes.add(resultSet.getString("tipo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposNomes;    
    }

    public List<Procedimento> EncontrarTarefasPorDescricao(String tarefa) {
        List<Procedimento> tarefasRetornadas = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM PROCEDIMENTOS Where descricao like '%"+tarefa+"%'")) {

            while (resultSet.next()) {
                Procedimento t = mapearTarefa(resultSet);
                tarefasRetornadas.add(t);      
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefasRetornadas;  
    }
}
