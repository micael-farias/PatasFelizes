package main.repositories;


import java.sql.*;
import java.util.Calendar;
import main.model.Adocao;
import main.model.Adotante;
import static main.utils.DateHelper.DateToCalendar;

public class AdocaoRepository extends BaseRepository<Adocao> {

    public AdocaoRepository() {
        super(Adocao.class);
    }
        
    public Adocao SalvarAdocao(int idAdocao, int idAnimal, Adotante adotante) throws SQLException {
        Adocao adocao = new Adocao();
        adocao.setId(idAdocao);
        adocao.setIdAnimal(idAnimal);
        adocao.setAdotante(adotante);
        adocao.setDataCadastro(Calendar.getInstance());

        if (idAdocao == -1) {
            return InserirAdocao(adocao);
        } else {
            return AtualizarAdocao(adocao);
        }
    }

    public Adocao InserirAdocao(Adocao adocao) throws SQLException {
        String sql = "INSERT INTO ADOCOES (IdAnimal, IdAdotante, DataCadastro) VALUES (?, ?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, adocao.getIdAnimal());
            preparedStatement.setInt(2, adocao.getAdotante().getId());
            preparedStatement.setTimestamp(3, new Timestamp(adocao.getDataCadastro().getTimeInMillis()));
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    adocao.setId(generatedId);
                    return adocao;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public Adocao AtualizarAdocao(Adocao adocao) throws SQLException {
        String sql = "UPDATE ADOCOES SET IdAnimal=?, IdAdotante=? WHERE Id=?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, adocao.getIdAnimal());
            preparedStatement.setInt(2, adocao.getAdotante().getId());
            preparedStatement.setInt(3, adocao.getId());
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return adocao;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return null;
    }

    public Adocao EncontrarAdocaoPorAnimal(int idAnimal) {

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM ADOCOES WHERE IdAnimal = " + idAnimal)) {

            while (resultSet.next()) {
                Adocao adocao = mapearAdocao(resultSet);
                return adocao;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Adocao mapearAdocao(ResultSet resultSet) throws SQLException {
        Adocao adocao = new Adocao();
        adocao.setId(resultSet.getInt("Id"));
        adocao.setIdAnimal(resultSet.getInt("IdAnimal"));
        adocao.setDataCadastro(DateToCalendar(resultSet.getTimestamp("DataCadastro")));
       
        int adotanteId = resultSet.getInt("IdAdotante");
        if (adotanteId > 0) {
            Adotante adotante = new AdotantesRepository().BuscarAdotantePorId(adotanteId);
            adocao.setAdotante(adotante);
        }
        return adocao;
    }

    public void DeletarAdocaoPorId(int id) throws Exception {
        Excluir(id);
    }
}
