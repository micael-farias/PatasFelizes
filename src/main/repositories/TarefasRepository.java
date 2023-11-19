package main.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import main.model.Animal;
import main.model.Tarefa;
import main.model.Voluntario;
import static main.utils.Constantes.PATH_IMAGES;

public class TarefasRepository {
       
    private static List<Tarefa> tarefas = new ArrayList<>();
    
    public Tarefa Salvar(int idTarefa, Voluntario voluntario, Animal animal, String descricao, Date data) {
       int index = EncontrarPosicaoTarefaPor(idTarefa);
       
       Tarefa tarefa;
       
       if(index == -1){
            tarefa = new Tarefa();
            tarefa.setDescricao(descricao);
            tarefa.setData(data);
            tarefa.setAnimal(animal);
            tarefa.setVoluntário(voluntario);
            tarefas.add(tarefa);  
       }else{
            tarefa = tarefas.get(index);
            tarefa.setDescricao(descricao);
            tarefa.setData(data);
            tarefa.setAnimal(animal);
            tarefa.setVoluntário(voluntario);
            tarefas.set(index, tarefa);  
       }
     
       return tarefa;        
    }
    
    public int EncontrarPosicaoTarefaPor(int id) {
        for(Tarefa t : tarefas){
            if(t.getId() == id){
                return tarefas.indexOf(t);
            }
        }
        
        return -1;
    }
    
    public List<Tarefa> ObterTarefas() {
        List<Tarefa> tarefasRetornadas = new ArrayList<>(tarefas);    
        Collections.sort(tarefasRetornadas, Comparator.comparing(Tarefa::getDataCadastro).reversed());
        return tarefasRetornadas;
    }
}
