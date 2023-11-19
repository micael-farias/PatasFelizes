package main.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Despesa;
public class DespesaRepository {
    
    private static List<Despesa> despesas = new ArrayList<>();

    public DespesaRepository(){
        if(despesas.size() == 0) a();
    }
    
    public Despesa Cadastrar(String descricao, double valor, Date data){
        Despesa despesa = new Despesa();
        despesa.setDescricao(descricao);
        despesa.setData(data);
        despesa.setValor(valor);
        
        despesas.add(despesa);
        
        return despesa;
    }
    
    public List<Despesa> ObterDespesas(){
        List<Despesa> despesasRetornadas = new ArrayList<>(despesas);
        Collections.sort(despesasRetornadas, Comparator.comparing(Despesa::getDataCadastro).reversed());
        return despesasRetornadas;
    }
    
    public void a(){
        despesas.add(new Despesa("Alimentação", 50.0, new Date(), "Comida"));
        despesas.add(new Despesa("Transporte", 30.0, new Date(), "Transporte"));
        despesas.add(new Despesa("Moradia", 1000.0, new Date(), "Habitação"));
        despesas.add(new Despesa("Educação", 200.0, new Date(), "Educação"));
        despesas.add(new Despesa("Lazer", 80.0, new Date(), "Entretenimento"));
        despesas.add(new Despesa("Saúde", 120.0, new Date(), "Saúde"));
        despesas.add(new Despesa("Contas", 150.0, new Date(), "Serviços Públicos"));
        despesas.add(new Despesa("Roupas", 60.0, new Date(), "Vestuário"));
        despesas.add(new Despesa("Presentes", 50.0, new Date(), "Presentes"));
        despesas.add(new Despesa("Outros", 70.0, new Date(), "Outros"));

                   
    }

    public Set<String> ObterTiposDespesa() {
        Set<String> tiposDespesa = new HashSet<>();
        for(Despesa d : despesas){
            tiposDespesa.add(d.getTipo());
        }
        
        return tiposDespesa;
    }
    
}
