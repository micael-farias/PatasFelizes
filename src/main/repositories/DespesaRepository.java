package main.repositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Despesa;

public class DespesaRepository extends BaseRepository<Despesa> {

    public DespesaRepository() {
        super(Despesa.class);
    }


    public Despesa Salvar(int idDespesa, String descricao, Double valor, Calendar data, String tipo, boolean realizado) {
        Despesa despesa;

        if (idDespesa == -1) {
            despesa = new Despesa();
            despesa.setDescricao(descricao);
            despesa.setData(data);
            despesa.setValor(valor);
            despesa.setTipo(tipo);
            despesa.setRealizada(realizado);
            idDespesa = Inserir(despesa);
            despesa.setId(idDespesa);
        } else {
            despesa = EncontrarDespesaPor(idDespesa);
            despesa.setDescricao(descricao);
            despesa.setData(data);
            despesa.setValor(valor);
            despesa.setTipo(tipo);
            despesa.setRealizada(realizado);
            Atualizar(despesa);
        }

        return despesa;
    }

    public List<Despesa> ObterDespesas() {
        return new ArrayList<>(SelecionarTodos("*", null,"Data desc", Despesa.class));
    }

    public Set<String> ObterTiposDespesa() {
        return new HashSet<>( SelecionarTodos("TIPO", null,null, String.class));
    }

    public void Deletar(Despesa despesa) {
        Excluir(despesa);
    }

    public Despesa EncontrarDespesaPor(int idDespesa) {
        var despesas = SelecionarTodos("*", "ID = " + idDespesa, null, Despesa.class);
        if ( despesas != null ) return despesas.get(0);
        return null;
    }
}
