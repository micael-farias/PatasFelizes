package main.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import main.model.Despesa;
import main.repositories.DespesaRepository;

public class DespesaServices {

    DespesaRepository despesaRepository;
    
    public DespesaServices(){
        despesaRepository = new DespesaRepository();
    }
    
    public List<Despesa> ObterDespesas(){
        return despesaRepository.ObterDespesas();
    }    

    public Set<String> ObterTiposDespesa(){
        return despesaRepository.ObterTiposDespesa();
    }    
    
    public void Cadastrar(String descriao, String valor, LocalDate data, String pet, String tipo) {
        
    }
}
