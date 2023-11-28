package main.services;

import java.sql.SQLException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import main.App;
import main.enums.MensagemTipo;
import main.model.FiltroDespesa;
import main.repositories.TarefasRepository;
import main.utils.DateHelper;
import static main.utils.DateHelper.LocalDateParaCalendar;
import static main.utils.DateHelper.invalidString;

public class DespesaServices {

    DespesaRepository despesaRepository;
    AnimalRepository animalRepository; 
    ProcedimentoRepository procedimentoRepository;
    TarefasRepository tarefaRepository;
    public static FiltroDespesa filtro;

    
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
    
    public Despesa Cadastrar(int idDespesa, String descricao, double valor, LocalDate dataLocal, String animalString, String tipo, Boolean foiRealizado, byte[] fotoComprovante) {
        Calendar data = LocalDateParaCalendar(dataLocal);    
        boolean realizado = foiRealizado == null ? data.before(GetMidnightDate()) : foiRealizado;

        
        Animal animal = null;
        if(animalString != null && !animalString.isEmpty())
        {
            animal = animalRepository.EncontrarAnimalPorNome(animalString);
            if(animal == null){
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Animal não encontrado, verifique seu nome");
                return null;
            }  
        }       
        Despesa despesa;
        try{
            despesaRepository.BeginTransaction();
            if(idDespesa == -1){
                        despesa = despesaRepository.Salvar(idDespesa, descricao, valor, data, tipo, realizado, fotoComprovante);                

            }else{

            if(animal != null){
                Procedimento procedimento = procedimentoRepository.encontrarProcedimentosPorDespesa(idDespesa);
                despesa = despesaRepository.EncontrarDespesaPor(idDespesa);
                realizado = foiRealizado == null ? despesa.isRealizada(): foiRealizado;
                despesa = despesaRepository.Salvar(idDespesa, descricao, valor, data, tipo, realizado, fotoComprovante);                

                if(procedimento == null){
                    procedimentoRepository.Salvar(-1, descricao, data, tipo, despesa, null, animal, realizado);
                  }else{
                    procedimentoRepository.Salvar(procedimento.getId(), descricao, data, 
                        tipo, despesa, procedimento.getVoluntario(), animal, realizado);   
                    
                }
            }else{
                despesa = despesaRepository.Salvar(idDespesa, descricao, valor, data, tipo, realizado, fotoComprovante);                
            }}
        
            
            
            despesaRepository.CommitTransaction();
            
        }catch(Exception e){
            e.printStackTrace();
            String mensagem = idDespesa == -1 ? "cadastrar" : "atualizar";
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao " + mensagem + " a despesa");
            try {
                despesaRepository.RollbackTransaction();
            } catch (SQLException ex) {
                Logger.getLogger(DespesaServices.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            despesa= null;
        }
        return despesa;
               
    }

    public List<Despesa> ObterDespesasPorDescricao(String texto) {
        return despesaRepository.ObterDespesasPorDescricao(texto);
    }

    public List<Despesa> FiltrarDespesas(FiltroDespesa filtro) {
        int idAnimal = 0;
        
        if(!invalidString(filtro.getAnimal()))
        {
            Animal animal = animalRepository.EncontrarAnimalPorNome(filtro.getAnimal());
            if(animal == null){
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Animal não encontrado, verifique seu nome");
                return null;
            }  
            
            idAnimal = animal.getId();
        }  
        
        try {
            return despesaRepository.FiltrarDespesas(filtro, idAnimal);
        } catch (SQLException ex) {
            ex.printStackTrace();
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao filtrar despesas");
            return null;
        }
    }
    
}
