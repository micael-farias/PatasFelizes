package main.repositories;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import main.model.Procedimento;

public class DoacaoRepository extends BaseRepository<Doacao>{
    
    public static List<Doacao> doacoes = new ArrayList<>();

    public DoacaoRepository() {
        super(Doacao.class);
    }
    
     public Doacao Salvar(int idDoacao, String doador, Double valor, Calendar data, byte[] fotoComprovante) throws SQLException, IllegalAccessException{
       
       Doacao doacao;
       
       if(idDoacao == -1){
            doacao = new Doacao();
            doacao.setDoador(doador);
            doacao.setData(data);
            doacao.setValor(valor);
            doacao.setFotoComprovante(fotoComprovante);
            this.Inserir(doacao);  
       }else{
            doacao = new Doacao();
            doacao.setId(idDoacao);
            doacao.setDoador(doador);
            doacao.setData(data);
            doacao.setValor(valor);
            doacao.setFotoComprovante(fotoComprovante);
            Atualizar(doacao);  
       }
     
       return doacao;
    }

    public List<Doacao> ObterDoacoes() {
        List<Doacao> doacoesRetornadas = new ArrayList<>( SelecionarTodos("*", null, "Data desc", Doacao.class));
        Collections.sort(doacoesRetornadas, Comparator.comparing(Doacao::getData).reversed());
        return doacoesRetornadas;
    }

    public Set<String> ObterNomesDoadores() { 
        return new HashSet<>( SelecionarTodos("Doador", null, null, String.class));  
    }

    public double[] BuscarValoresDoacoesEDespesa() {
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


    
}
