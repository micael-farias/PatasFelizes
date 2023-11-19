/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import main.model.Animal;
import main.model.Despesa;
import main.model.Procedimento;
import main.model.Voluntario;
import main.repositories.AnimalRepository;
import main.repositories.DespesaRepository;
import main.repositories.ProcedimentoRepository;
import main.repositories.TarefasRepository;
import main.repositories.VoluntarioRepository;
import static main.utils.DateHelper.LocalDateParaDate;

public class ProcedimentoService {

    private ProcedimentoRepository procedimentoRepository;
    private VoluntarioRepository voluntarioRepository;
    private AnimalRepository animalRepository;
    private DespesaRepository despesaRepository;
    private TarefaServices tarefasService;
    
    public ProcedimentoService(){
        procedimentoRepository = new ProcedimentoRepository();
        voluntarioRepository = new VoluntarioRepository();
        animalRepository = new AnimalRepository();
        despesaRepository = new DespesaRepository();
        tarefasService = new TarefaServices();
    }
    
    public Procedimento Salvar(int idProcedimento, String descricao, LocalDate dataLocal, String tipo, String valorString, String voluntarioString, int idAnimal) {
        Date data = LocalDateParaDate(dataLocal);        
        Double valor = Double.valueOf(valorString);        
        
        Voluntario voluntario = voluntarioRepository.EncontrarVoluntarioPor(voluntarioString);
        Animal animal = animalRepository.EncontrarAnimalPor(idAnimal);
        
        Despesa despesa = null;
        if(valor != 0.0) despesa = despesaRepository.Cadastrar(descricao, valor, data);
        tarefasService.Salvar();
        
        return procedimentoRepository.Salvar(idProcedimento, descricao, data, tipo, despesa, voluntario, animal);
    }
    
    public void SalvarProcedimentosSemAnimal(Animal animal) {
        List<Procedimento> procedimentosSemAnimal = procedimentoRepository.BuscarProcedimentosSemAnimal();
        for(Procedimento p : procedimentosSemAnimal){
            p.setAnimal(animal);
            procedimentoRepository.Salvar(p.getId(), p.getDescricao(), p.getDataCadastro(), p.getTipo(), p.getDespesa(), p.getVoluntario(), p.getAnimal());
        }           
    }

    public List<Procedimento> EncontrarProcedimentosPor(int idAnimal) {
        return procedimentoRepository.EncontrarProcedimentosPor(idAnimal);
    }
    
}
