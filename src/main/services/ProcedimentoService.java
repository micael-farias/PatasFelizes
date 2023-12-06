 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import main.model.Procedimento;
import main.model.Tarefa;
import main.model.Voluntario;
import main.repositories.AnimalRepository;
import main.repositories.DespesaRepository;
import main.repositories.ProcedimentoRepository;
import main.repositories.TarefasRepository;
import main.repositories.VoluntarioRepository;
import main.utils.DateHelper;
import static main.utils.DateHelper.DataParaStringReduced;
import static main.utils.DateHelper.GetMidnightDate;
import static main.utils.DateHelper.LocalDateParaCalendar;
import static main.utils.DateHelper.LocalDateParaDate;
import static main.utils.DateHelper.invalidString;
import main.utils.EmailSenderThread;
import main.utils.EmailTemplate;
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
    
   
    
    public Procedimento Salvar(int idProcedimento, String descricao, LocalDate dataLocal, String tipo, double valor, String voluntarioString, int idAnimal, Boolean foiRealizado) {
        Calendar data = LocalDateParaCalendar(dataLocal); 
             
        boolean realizado = foiRealizado == null ? data.before(GetMidnightDate()) : foiRealizado;
        boolean enviaEmail = !data.before(GetMidnightDate()) && voluntarioString != null;
                
        Despesa despesa = null;
        Voluntario voluntario = null;
        Animal animal = null;

        if(!invalidString(voluntarioString)){
            voluntario = voluntarioRepository.EncontrarVoluntarioPor(voluntarioString);
            if(voluntario == null){
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Não foi encontrado um voluntario, verifique a ortografia", null);
                return null;
            }
        }
        
        if(idAnimal != -1){
            animal = animalRepository.EncontrarAnimalPor(idAnimal);
            if(animal == null){
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "Não foi encontrado um animal, verifique a ortografia", null);
                return null;

            }       
        }        

        Procedimento procedimento;

        try{
            procedimentoRepository.BeginTransaction();
            
            if(idProcedimento == -1){
                if(valor != 0.0) despesa = despesaRepository.Salvar(-1, descricao, valor, data, tipo, realizado, null);       


                procedimento = procedimentoRepository.Salvar(idProcedimento, descricao, data, tipo, despesa, voluntario, animal, realizado);
                            
                    if(enviaEmail && voluntario != null){
                        String template = EmailTemplate.criarCorpoEmail(descricao, DateHelper.CalendarParaString(data), voluntarioString);
                        new EmailSenderThread(voluntario.getEmail(), "Nova tarefa pra você", template, null).start();

                    }
                    
            }else{
                procedimento = procedimentoRepository.encontrarProcedimentosPorId(idProcedimento);
                realizado = foiRealizado == null ? procedimento.isRealizado() : foiRealizado;
                Tarefa tarefa = null;

                if(valor != 0.0){
                    if(procedimento.getDespesa() == null){
                        despesa = despesaRepository.Salvar(-1, descricao, valor, data, tipo, realizado, null);

                    }
                    else
                    {
                        despesa = despesaRepository.Salvar(procedimento.getDespesa().getId(), descricao, valor, data, tipo, realizado, procedimento.getDespesa().getFotoComprovante());

                    }
                }else if(valor == 0 && procedimento.getDespesa() != null){
                    despesaRepository.Deletar(procedimento.getDespesa().getId());
                }

                procedimento = procedimentoRepository.Salvar(idProcedimento, descricao, data, tipo, despesa, voluntario, animal, realizado);
            }

            procedimento.setVoluntario(voluntario);
            procedimentoRepository.CommitTransaction();
            
            return procedimento;
        }catch(Exception e){
            String mensagem = idProcedimento == -1 ? "cadastrar" : "atualizar";
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao " + mensagem + " o procedimento", null);
          e.printStackTrace();
            try {
                procedimentoRepository.RollbackTransaction();
            } catch (SQLException ex) {
                Logger.getLogger(ProcedimentoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
        }
        
      
        
        
    }

    public List<Procedimento> EncontrarProcedimentosPor(int idAnimal) {
        return procedimentoRepository.encontrarProcedimentosPor(idAnimal);
    }
        
    public List<Procedimento> EncontrarProcedimentosPor(String descricao, int idAnimal) {
        return procedimentoRepository.encontrarProcedimentosPor(descricao, idAnimal);
    }
    
    
    public Set<String> ObterNomesTiposProcedimento() {
        return procedimentoRepository.encontrarNomesTipos();
    }

    public Procedimento ObterProcedimentoPorDespesa(int idDespesa) {
        return procedimentoRepository.encontrarProcedimentosPorDespesa(idDespesa);
    }
    
    public int Excluir(int idProcedimento){
        try {
            procedimentoRepository.Excluir(idProcedimento);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao deletar procedimento", null);
            return 0;
        }
    }
    
}
