package main.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.Alteracao;
import static main.utils.DateHelper.DateToCalendar;

public class AlteracoesRepository extends BaseRepository<Alteracao>{

    private static List<Alteracao> alteracoes = new ArrayList<>();
    
    public AlteracoesRepository() {
        super(Alteracao.class);
    }

    private Alteracao mapearAuditorios(ResultSet resultSet) throws SQLException {

        Alteracao alteracao = new Alteracao();
        
        alteracao.setId(resultSet.getInt("id"));
        alteracao.setTabelaAfetada(resultSet.getString("tabelaAfetada"));
        alteracao.setIdRegistroAfetado(resultSet.getInt("idRegistroAfetado"));
        alteracao.setDataAlteracao(DateToCalendar(resultSet.getDate("dataAlteracao")));
        alteracao.setDescritor(resultSet.getString("descritor"));
        alteracao.setColunaAlterada(resultSet.getString("colunaAlterada"));
        alteracao.setValorAntigo(resultSet.getString("valorAntigo"));
        alteracao.setValorNovo(resultSet.getString("valorNovo"));
        alteracao.formatarMensagem();
        return alteracao;
    }

    public List<Alteracao> EncontrarAlteracoes() {
        List<Alteracao> alteracoesRetornadas = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM ALTERACOES ORDER BY DATAALTERACAO DESC")) {

            while (resultSet.next()) {
                Alteracao t = mapearAuditorios(resultSet);
                alteracoesRetornadas.add(t);      
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        alteracoes.clear();
        alteracoes.addAll(alteracoesRetornadas);
        return alteracoesRetornadas;  
    }

    public List<Alteracao> EncontrarAlteracoesPorDescricao(String filtro) {
       List<Alteracao> alteracoesRetornadas = new ArrayList<>();
       
       for (Alteracao alteracao : AlteracoesRepository.alteracoes) {

           if (alteracao.getMensagem().toLowerCase().contains(filtro.toLowerCase())) {
                alteracoesRetornadas.add(alteracao);
            }
        }

        return alteracoesRetornadas;      
    }
}
