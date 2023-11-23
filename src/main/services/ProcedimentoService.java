 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.services;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import main.model.Animal;
import main.model.Despesa;
import main.model.Procedimento;
import main.model.Tarefa;
import main.model.Voluntario;
import main.repositories.AnimalRepository;
import main.repositories.DespesaRepository;
import main.repositories.ProcedimentoRepository;
import main.repositories.TarefasRepository;
import main.repositories.VoluntarioRepository;
import static main.utils.DateHelper.DataParaStringReduced;
import static main.utils.DateHelper.GetMidnightDate;
import static main.utils.DateHelper.LocalDateParaDate;
import main.utils.EmailSenderThread;
import static main.utils.NumberHelper.DoubleParse;

public class ProcedimentoService {

    private ProcedimentoRepository procedimentoRepository;
    private VoluntarioRepository voluntarioRepository;
    private AnimalRepository animalRepository;
    private DespesaRepository despesaRepository;
    private TarefasRepository tarefasRepository;
    
    public ProcedimentoService(){
        procedimentoRepository = new ProcedimentoRepository();
        voluntarioRepository = new VoluntarioRepository();
        animalRepository = new AnimalRepository();
        despesaRepository = new DespesaRepository();
        tarefasRepository = new TarefasRepository();
    }
    
    public Procedimento Salvar(int idProcedimento, String descricao, LocalDate dataLocal, String tipo, String valorString, String voluntarioString, int idAnimal, Boolean foiRealizado) {
        Date data = LocalDateParaDate(dataLocal); 
             
        boolean realizado = foiRealizado == null ? data.before(GetMidnightDate()) : foiRealizado;
        boolean enviaEmail = !data.before(GetMidnightDate());
                
        Double valor = DoubleParse(valorString);       
        Despesa despesa = null;
                
        Voluntario voluntario = voluntarioRepository.EncontrarVoluntarioPor(voluntarioString);
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(data);
                    
                  
        Animal animal = animalRepository.EncontrarAnimalPor(idAnimal);
        if(idProcedimento == -1){
            if(valor != 0.0) despesa = despesaRepository.Salvar(-1, descricao, valor, dataCalendar, tipo, realizado);       
            Tarefa tarefa = tarefasRepository.Salvar(-1, voluntario, animal,descricao, dataCalendar, tipo, realizado);
            if(enviaEmail){
                     new EmailSenderThread(voluntario.getEmail(), "Nova tarefa pra você", "Patas felizes tem uma nova tarefa").start();
            }
           
            return procedimentoRepository.Salvar(idProcedimento, descricao, dataCalendar, tipo, despesa, voluntario, tarefa, animal, realizado);
        }else{
            Procedimento procedimento = procedimentoRepository.encontrarProcedimentosPorId(idProcedimento);
            Tarefa tarefa = null;
            if(procedimento.getTarefa() != null){
                tarefa = tarefasRepository.Salvar(procedimento.getTarefa().getId(), voluntario, animal, descricao, dataCalendar, tipo, realizado);
                if(enviaEmail){
                     new EmailSenderThread(voluntario.getEmail(), "Nova tarefa pra você", "Patas felizes tem uma nova tarefa").start();
                }

            }
            
            if(valor != 0.0){
                if(procedimento.getDespesa() == null){
                    despesa = despesaRepository.Salvar(-1, descricao, valor, dataCalendar, tipo, realizado);

                }
                else
                {
                    despesa = despesaRepository.Salvar(procedimento.getDespesa().getId(), descricao, valor, dataCalendar, tipo, realizado);

                }
            }else if(valor == 0 && procedimento.getDespesa() != null){
                despesaRepository.Deletar(procedimento.getDespesa());
            }
            
            return procedimentoRepository.Salvar(idProcedimento, descricao, dataCalendar, tipo, despesa, voluntario, tarefa, animal, realizado);
        }
        
        
    }

    public List<Procedimento> EncontrarProcedimentosPor(int idAnimal) {
        return procedimentoRepository.encontrarProcedimentosPor(idAnimal);
    }
    
    
    public Set<String> ObterNomesTiposProcedimento() {
        return procedimentoRepository.encontrarNomesTipos();
    }

    public Procedimento ObterProcedimentoPorDespesa(int idDespesa) {
        return procedimentoRepository.encontrarProcedimentosPorDespesa(idDespesa);
    }
    
}
