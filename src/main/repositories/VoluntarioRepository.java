package main.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Voluntario;
import static main.utils.Constantes.PATH_IMAGES;

public class VoluntarioRepository extends BaseRepository<Voluntario>{

    public VoluntarioRepository() {
        super(Voluntario.class);
    }
    
    public Voluntario EncontrarVoluntarioPor(String nome){
        return SelecionarTodos("*", "NOME = '"+nome+"'", null, Voluntario.class).get(0);
    }
    
    public Voluntario EncontrarVoluntarioPor(int id){
        return SelecionarTodos("*", "ID = '"+id+"'", null, Voluntario.class).get(0);
    }
    
    public  Set<String> EncontrarNomesVoluntarios(){
        return new HashSet<>(SelecionarTodos("NOME", null, null, String.class));
    }
    
    public List<Voluntario> ObterVoluntarios(){
        return new ArrayList<>(SelecionarTodos("*", null, "NOME DESC", Voluntario.class));
    }

    public void Salvar(int idVoluntario, String nome, String email, String telefone, byte[] fotoVoluntario) {
       
       Voluntario voluntario;
      
       if(idVoluntario == -1){
            voluntario = new Voluntario();
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);     
            voluntario.setFoto(fotoVoluntario);
            this.Inserir(voluntario);  
       }else{
            voluntario = new Voluntario();
            voluntario.setId(idVoluntario);
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);  
            voluntario.setFoto(fotoVoluntario);
            Atualizar(voluntario);  
       }
    }
    
}
