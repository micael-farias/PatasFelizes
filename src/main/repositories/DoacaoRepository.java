package main.repositories;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Despesa;
import main.model.Doacao;
import main.model.FiltroDespesa;
import main.model.Procedimento;
import main.utils.DateHelper;
import static main.utils.DateHelper.DateToCalendar;
import static main.utils.DateHelper.invalidString;

public class DoacaoRepository extends BaseRepository<Doacao>{
    
    public static List<Doacao> doacoes = new ArrayList<>();

    public DoacaoRepository() {
        super(Doacao.class);
    }
    
     public Doacao Salvar(int idDoacao, String doador, Double valor, Date data, byte[] fotoComprovante) throws SQLException, IllegalAccessException{
       
       Doacao doacao;
       
       if(idDoacao == -1){
            doacao = new Doacao();
            doacao.setDoador(doador);
            doacao.setData(data);
            doacao.setValor(valor);
            doacao.setFotoComprovante(fotoComprovante);
            this.inserirDoacao(doacao);  
       }else{
            doacao = new Doacao();
            doacao.setId(idDoacao);
            doacao.setDoador(doador);
            doacao.setData(data);
            doacao.setValor(valor);
            doacao.setFotoComprovante(fotoComprovante);
            atualizarDoacao(doacao);  
       }
     
       return doacao;
    }
     
     private Doacao inserirDoacao(Doacao doacao) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Doacoes (Doador, Valor, Data, FotoComprovante, DataCadastro) VALUES (?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {

        System.out.println(DateHelper.DataParaString(doacao.getData()));
        statement.setString(1, doacao.getDoador());
        statement.setDouble(2, doacao.getValor());
        statement.setTimestamp(3, new Timestamp(doacao.getData().getTime()));
        statement.setBytes(4, doacao.getFotoComprovante());
        statement.setTimestamp(5, new Timestamp(doacao.getDataCadastro().getTime()));

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                doacao.setId(generatedId);
                return doacao;
            }
        }
    } catch (SQLException e) {
        throw e;
    }

    return null;
}

private Doacao atualizarDoacao(Doacao doacao) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(
            "UPDATE Doacoes SET Doador=?, Valor=?, Data=?, FotoComprovante=?  WHERE Id=?")) {
        System.out.println(DateHelper.DataParaString(doacao.getData()));
        System.out.println(doacao.getData().getTime());
        var tiume = new Timestamp(doacao.getData().getTime());
        System.out.println(tiume.getTime());
        statement.setString(1, doacao.getDoador());
        statement.setDouble(2, doacao.getValor());
        statement.setTimestamp(3, tiume);
        statement.setBytes(4, doacao.getFotoComprovante());
        statement.setInt(5, doacao.getId());
        System.out.println(statement);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            return doacao;
        }
    } catch (SQLException e) {
        throw e;
    }

    return null;
}


    public List<Doacao> ObterDoacoes() {
        List<Doacao> doacoes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM DOACOES ORDER BY DATACADASTRO DESC")) {

            while (resultSet.next()) {
                Doacao doacao = mapearDoacao(resultSet);
                doacoes.add(doacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doacoes;
    }

    public Set<String> ObterNomesDoadores() { 
        return new HashSet<>( SelecionarTodos("Doador", null, null, String.class));  
    }

    public double[] BuscarValoresDoacoesEDespesa()  {
        double[] valores = new double[2]; 
        int pos = 0;
        
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COALESCE(SUM(DE.VALOR), 0) as DES FROM DESPESAS DE UNION ALL SELECT COALESCE(SUM(DO.VALOR), 0) as DOA FROM DOACOES DO")) {

            while(rs.next()) {
                valores[pos++] = rs.getDouble("DES"); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valores;
    }

    public List<Doacao> ObterDoacoesPorDescricao(String doador) {
        return SelecionarTodos("*", "DOADOR LIKE '%"+doador+"%'", null, Doacao.class);
    }

    public List<Doacao> FiltrarDoacoes(FiltroDespesa filtro) throws SQLException {
        List<Doacao> doacoes = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM DOACOES WHERE 1 = 1");

        if (filtro.getDataFinal() != null && filtro.getDataInicial() != null) {
            sql.append(" AND Data BETWEEN ? AND ?");
        }
        
        if (!invalidString(filtro.getOrdenacao())) {
            sql.append(" ORDER BY ").append(filtro.getOrdenacao());
        }
        
        System.out.println(sql);

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int parametroIndex = 1;

            if (filtro.getDataFinal() != null && filtro.getDataInicial() != null) {
                statement.setTimestamp(parametroIndex++, new Timestamp(filtro.getDataInicial().getTimeInMillis()));
                statement.setTimestamp(parametroIndex++, new Timestamp(filtro.getDataFinal().getTimeInMillis()));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Doacao doacao = mapearDoacao(resultSet);
                    doacoes.add(doacao);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return doacoes;
    }    
    
    public Doacao mapearDoacao(ResultSet resultSet) throws SQLException {
        Doacao doacao = new Doacao();
        doacao.setId(resultSet.getInt("Id"));
        doacao.setDoador(resultSet.getString("Doador"));
        doacao.setValor(resultSet.getDouble("Valor"));
        doacao.setData(new Date(resultSet.getTimestamp("Data").getTime()));
        doacao.setDataCadastro(new Date(resultSet.getTimestamp("DataCadastro").getTime()));
        doacao.setFotoComprovante(resultSet.getBytes("FotoComprovante"));
 if(doacao.getId() == 7){
            System.out.println(resultSet.getTimestamp("Data"));
            System.out.println(DateHelper.DataParaString(resultSet.getTimestamp("Data")));
            System.out.println(DateHelper.DataParaString(doacao.getData()));
        }
        return doacao;
    }

    
}
