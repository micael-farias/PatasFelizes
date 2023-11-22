package main.repositories;

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

public class DoacaoRepository extends BaseRepository<Doacao>{
    
    public static List<Doacao> doacoes = new ArrayList<>();

    public DoacaoRepository() {
        super(Doacao.class);
    }
    
     public Doacao Salvar(int idDoacao, String doador, Double valor, Calendar data, byte[] fotoComprovante){
       
       Doacao doacao;
       
       if(idDoacao == -1){
            doacao = new Doacao();
            doacao.setDoador(doador);
            doacao.setData(data);
            doacao.setValor(valor);
            doacao.setFotoComprovante(fotoComprovante);
            Inserir(doacao);  
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
}
