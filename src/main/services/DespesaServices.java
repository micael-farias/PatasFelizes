package main.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import main.model.Animal;
import main.model.Despesa;
import main.model.Procedimento;
import main.model.Tarefa;
import main.repositories.AnimalRepository;
import main.repositories.DespesaRepository;
import main.repositories.ProcedimentoRepository;
import static main.utils.DateHelper.GetMidnightDate;
import static main.utils.DateHelper.LocalDateParaDate;
import static main.utils.NumberHelper.DoubleParse;
import java.sql.Timestamp;
import java.util.Calendar;
import main.repositories.TarefasRepository;
import static main.utils.DateHelper.LocalDateParaCalendar;

public class DespesaServices {

    DespesaRepository despesaRepository;
    AnimalRepository animalRepository; 
    ProcedimentoRepository procedimentoRepository;
    TarefasRepository tarefaRepository;
            
    
    public DespesaServices(){
        despesaRepository = new DespesaRepository();
        animalRepository = new AnimalRepository();
        procedimentoRepository = new ProcedimentoRepository();
        tarefaRepository = new TarefasRepository();
    }
    
    public List<Despesa> ObterDespesas(){
        return despesaRepository.ObterDespesas();
    }    

    public Set<String> ObterTiposDespesa(){
        return despesaRepository.ObterTiposDespesa();
    }    
    
    public Despesa Cadastrar(int idDespesa, String descricao, String valorString, LocalDate dataLocal, String animalString, String tipo, Boolean foiRealizado) {
        Calendar data = LocalDateParaCalendar(dataLocal);        
        Double valor = DoubleParse(valorString);  
        boolean realizado = foiRealizado == null ? data.before(GetMidnightDate()) : foiRealizado;

        
        Animal animal = null;
        if(animalString != null && !animalString.isEmpty())
        {
            animal = animalRepository.EncontrarAnimalPorNome(animalString);
            if(animal == null){
                // mensagem de erro
                return null;
            }  
        }       
        
        Despesa despesa = despesaRepository.Salvar(idDespesa, descricao, valor, data, tipo, realizado);                
        if(animal != null){
            Procedimento procedimento = procedimentoRepository.encontrarProcedimentosPorDespesa(idDespesa);

            if(procedimento == null){
                procedimentoRepository.Salvar(-1, descricao, data, tipo, despesa, null, null, animal, realizado);
                tarefaRepository.Salvar(-1, null, animal, descricao, data, tipo, realizado);
            }else{
                procedimentoRepository.Salvar(procedimento.getId(), descricao, data, 
                    tipo, despesa, procedimento.getVoluntario(), procedimento.getTarefa(), animal, realizado);   
                tarefaRepository.Salvar(procedimento.getTarefa().getId(), procedimento.getTarefa().getVoluntario(),
                        animal, descricao, data, tipo, realizado);  
            }
        }
        
        
        return despesa;
               
    }
}
