package main.services;

import java.util.List;
import main.model.Tarefa;
import main.repositories.TarefasRepository;

public class TarefaServices {

    TarefasRepository tarefasRepository;
    
    public TarefaServices(){
        tarefasRepository = new TarefasRepository();
    }
    
    public List<Tarefa> ObterTarefas(){
        return tarefasRepository.ObterTarefas();
    }    
    
    public void Salvar(){
    
    }
}
