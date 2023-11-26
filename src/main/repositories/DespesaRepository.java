package main.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.*;
import main.model.Despesa;

public class DespesaRepository extends BaseRepository<Despesa> {

    public DespesaRepository() {
        super(Despesa.class);
    }


    public Despesa Salvar(int idDespesa, String descricao, Double valor, Calendar data, String tipo, boolean realizado, byte[] fotoComprovante) throws IllegalAccessException, SQLException{
        Despesa despesa;

        if (idDespesa == -1) {
            despesa = new Despesa();
            despesa.setDescricao(descricao);
            despesa.setData(data);
            despesa.setValor(valor);
            despesa.setTipo(tipo);
            despesa.setRealizada(realizado);
            despesa.setFotoComprovante(fotoComprovante);
            idDespesa = Inserir(despesa);
            despesa.setId(idDespesa);
        } else {
            despesa = EncontrarDespesaPor(idDespesa);
            despesa.setDescricao(descricao);
            despesa.setData(data);
            despesa.setValor(valor);
            despesa.setTipo(tipo);
            despesa.setFotoComprovante(fotoComprovante);
            despesa.setRealizada(realizado);
            atualizarDespesa(despesa);
        }

        return despesa;
    }
    
    private Despesa atualizarDespesa(Despesa despesa) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(
                 "UPDATE Despesas SET descricao=?, valor=?, data=?, tipo=?, realizada=?, dataCadastro=?, fotoComprovante=? WHERE id=?")) {

        statement.setString(1, despesa.getDescricao());
        statement.setDouble(2, despesa.getValor());
        statement.setTimestamp(3, new Timestamp(despesa.getData().getTimeInMillis()));
        statement.setString(4, despesa.getTipo());
        statement.setBoolean(5, despesa.isRealizada());
        statement.setTimestamp(6, new Timestamp(despesa.getDataCadastro().getTimeInMillis()));
        statement.setBytes(7, despesa.getFotoComprovante());
        statement.setInt(8, despesa.getId());

        System.out.println("SQL gerado: " + statement.toString());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            return despesa;
        }
    } catch (SQLException e) {
        throw e;
    }

    return null;
}


    public List<Despesa> ObterDespesas() {
        return new ArrayList<>(SelecionarTodos("*", null,"Data desc", Despesa.class));
    }

    public Set<String> ObterTiposDespesa() {
        return new HashSet<>( SelecionarTodos("TIPO", null,null, String.class));
    }

    public void Deletar(Despesa despesa) {
        //Excluir(despesa);
    }

    public Despesa EncontrarDespesaPor(int idDespesa) {
        var despesas = SelecionarTodos("*", "ID = " + idDespesa, null, Despesa.class);
        if ( despesas != null ) return despesas.get(0);
        return null;
    }

    public List<Despesa> ObterDespesasPorDescricao(String desc) {
        return SelecionarTodos("*", "DESCRICAO LIKE '%"+desc+"%'", null, Despesa.class);
    }
}
