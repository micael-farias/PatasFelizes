package main.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.App;
import main.enums.MensagemTipo;
import main.model.Animal;
import main.model.Despesa;
import main.model.Doacao;
import main.model.FiltroDespesa;
import main.model.Procedimento;
import main.model.Tarefa;
import main.model.Voluntario;
import main.repositories.AnimalRepository;
import main.repositories.DespesaRepository;
import main.repositories.ProcedimentoRepository;
import main.repositories.TarefasRepository;
import static main.utils.DateHelper.DataParaStringReduced;
import static main.utils.DateHelper.GetMidnightDate;
import static main.utils.DateHelper.LocalDateParaCalendar;
import static main.utils.DateHelper.LocalDateParaDate;
import main.utils.EmailSenderThread;

public class TarefaServices {

    TarefasRepository tarefasRepository;
    VoluntarioService voluntarioService;
    AnimalRepository animalRepository;
    DespesaRepository despesaRepository;
    ProcedimentoRepository procedimentoRepository;
    public static FiltroDespesa filtro;
    
    public TarefaServices(){
        tarefasRepository = new TarefasRepository();
        voluntarioService = new VoluntarioService();
        animalRepository = new AnimalRepository();
        despesaRepository = new DespesaRepository();
        procedimentoRepository = new ProcedimentoRepository();
    }
    
    public List<Procedimento> ObterTarefas(){
        return tarefasRepository.ObterTarefas();
    }    
    
    public Procedimento Salvar(int idTarefa, String voluntarioString, String animalString, String descricao, LocalDate dataLocal, String tipo, Boolean foiRealizado){
          
        
        Voluntario voluntario = null;
       
        if(voluntarioString != null){
            voluntario = voluntarioService.ObterVoluntarioPorNome(voluntarioString);
            if(voluntario == null){
                // mensagem de erro
                return null;
            }
        }
        Animal animal = null;
        if(animalString != null && !animalString.isEmpty())
        {
            animal = animalRepository.EncontrarAnimalPorNome(animalString);
            if(animal == null){
                // mensagem de erro
                return null;
            }
            
        }
        
        Calendar data = LocalDateParaCalendar(dataLocal);    
        boolean realizado = foiRealizado == null ? data.before(GetMidnightDate()) : foiRealizado;
        boolean enviaEmail = !data.before(GetMidnightDate());
        Procedimento procedimento = null;
        try{
            tarefasRepository.BeginTransaction();
            //enviar email para a pessoa



            if(idTarefa == -1){
                if(animal != null){
                    procedimento = procedimentoRepository.Salvar(-1, descricao, data, tipo, null, voluntario, animal, realizado);
                                if(enviaEmail && voluntario != null){
                new EmailSenderThread(voluntario.getEmail(), "Nova tarefa pra você", "Patas felizes tem uma nova tarefa", null).start();
            }
                }
            }else{               
                //if(animal != null){
                    procedimento = procedimentoRepository.encontrarProcedimentosPorTarefa(idTarefa);
                    realizado = foiRealizado == null ? procedimento.isRealizado() : foiRealizado;

                    if(procedimento.getDespesa() == null){
                      procedimento =  procedimentoRepository.Salvar(procedimento.getId(), descricao, data, tipo, null, voluntario, animal, realizado);

                    }else{
                      despesaRepository.Salvar(procedimento.getDespesa().getId(), descricao, procedimento.getDespesa().getValor(), data, tipo, realizado, procedimento.getDespesa().getFotoComprovante());
                      procedimento =  procedimentoRepository.Salvar(procedimento.getId(), descricao, data, tipo, procedimento.getDespesa(), voluntario, animal, realizado);
                    }
               // }
            }
            
            tarefasRepository.CommitTransaction();
        }catch(Exception e){
            try {
                tarefasRepository.RollbackTransaction();
            } catch (SQLException ex) {
                Logger.getLogger(TarefaServices.class.getName()).log(Level.SEVERE, null, ex);
            }
            procedimento = null;
        }
        
               
        return procedimento;
    }

    public Set<String> ObterNomesTiposTarefa() {
        return tarefasRepository.obterNomesTiposTarefa();
    }

    public List<Procedimento> EncontrarTarefasPorDescricao(String tarefa) {
        return tarefasRepository.EncontrarTarefasPorDescricao(tarefa);
    }

    public List<Procedimento> FiltrarTarefas(FiltroDespesa filtro) {
        try {
            return procedimentoRepository.FiltrarProcedimentos(filtro);
        } catch (SQLException ex) {
            ex.printStackTrace();
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha em filtrar as tarefas", null);
            return null;
        }
    }
    
    public int Excluir(int id) {
        try {
            procedimentoRepository.Excluir(id);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao deletar tarefa", null);
            return 0;
        }
    }
}
