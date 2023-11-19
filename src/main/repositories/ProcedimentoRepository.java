package main.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import main.model.Animal;
import main.model.Despesa;
import main.model.Procedimento;
import main.model.Voluntario;

public class ProcedimentoRepository {

    private static List<Procedimento> procedimentos = new ArrayList<>();
    
    public Procedimento Salvar(int idProcedimento, String descricao, Date data, String tipo, Despesa despesa, Voluntario voluntario, Animal animal) {
       int index = EncontrarPosicaoProcedimentoPor(idProcedimento);
       
       Procedimento procedimento;
       
       if(index == -1){
            procedimento = new Procedimento();
            procedimento.setDescricao(descricao);
            procedimento.setData(data);
            procedimento.setTipo(tipo);
            procedimento.setVoluntario(voluntario);
            procedimento.setDespesa(despesa);
            procedimento.setAnimal(animal);
            procedimentos.add(procedimento);  
       }else{
            procedimento = procedimentos.get(index);
            procedimento.setDescricao(descricao);
            procedimento.setData(data);
            procedimento.setTipo(tipo);
            procedimento.setVoluntario(voluntario);
            procedimento.setDespesa(despesa);
            procedimento.setAnimal(animal);
            procedimentos.set(index, procedimento);  
       }
     
       return procedimento;        
    }
    
   

    public List<Procedimento> EncontrarProcedimentosPor(int idAnimal) {
        List<Procedimento> procedimentosAnimal = new ArrayList<>();
        for(Procedimento p : procedimentos){
            if(p.getAnimal().getId() == idAnimal){
                procedimentosAnimal.add(p);
            }
        }
        
        Collections.sort(procedimentosAnimal, Comparator.comparing(Procedimento::getDataCadastro).reversed());
        return procedimentosAnimal;
    }
    
    public int EncontrarPosicaoProcedimentoPor(int id) {
        for(Procedimento p : procedimentos){
            if(p.getId() == id){
                return procedimentos.indexOf(p);
            }
        }
        
        return -1;
    }

    public List<Procedimento> BuscarProcedimentosSemAnimal() {
        List<Procedimento> procedimentosSemAnimal = new ArrayList<>();
        for(Procedimento p : procedimentos){
            if(p.getAnimal() == null){
                procedimentosSemAnimal.add(p);
            }
        }
        
        return procedimentosSemAnimal;    }
    
}
