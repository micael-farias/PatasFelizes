package main.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import main.model.Voluntario;
import static main.utils.Constantes.PATH_IMAGES;

public class VoluntarioRepository {
    
    private static final List<Voluntario> voluntarios = new ArrayList<>();
    
    public VoluntarioRepository(){
        if (voluntarios.size() == 0) a();       
    }
    
    public Voluntario EncontrarVoluntarioPor(String nome){
         for(Voluntario v : voluntarios){
             if(v.getNome().contains(nome)){
                  return v;
             }
         }
         return null;
    }
    
    public List<Voluntario> ObterVoluntarios(){
        List<Voluntario> voluntariosRetornados = new ArrayList<>();
        Collections.sort(voluntariosRetornados, Comparator.comparing(Voluntario::getDataCadastro).reversed());
        return voluntariosRetornados;
    }

    public void Salvar(int idVoluntario, String nome, String email, String telefone) {

       int index = EncontrarPosicaoVoluntarioPor(idVoluntario);
       
       Voluntario voluntario;
       
       if(index == -1){
            voluntario = new Voluntario();
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);        
            voluntarios.add(voluntario);  
       }else{
            voluntario = voluntarios.get(index);
            voluntario.setNome(nome);
            voluntario.setEmail(email);
            voluntario.setTelefone(telefone);  
            voluntarios.set(index, voluntario);  
       }
    }
    
    public int EncontrarPosicaoVoluntarioPor(int id) {
        for(Voluntario v : voluntarios){
            if(v.getId() == id){
                return voluntarios.indexOf(v);
            }
        }
        
        return -1;
    }
    
    public void a(){
        voluntarios.add(new Voluntario("Alexandre Toledo", PATH_IMAGES +"alexandre.jpeg", "alexandre@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Dinah Toledo", PATH_IMAGES +"dina.jpeg", "dinah@toledo.com.br","85997654398"));
        voluntarios.add(new Voluntario("Emanoela Maciel", PATH_IMAGES + "manu.jpeg", "manu@patasfelises.com.br", "88997652491"));
        voluntarios.add(new Voluntario("Graziella Rodrigues", PATH_IMAGES+"grazi.jpg", "grazi@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Michael Farias", PATH_IMAGES+"michael.png", "michael@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Pedro Emanuel", PATH_IMAGES+"pedro.png", "pedro@patasfelizes.com.br","85997654398"));
        voluntarios.add(new Voluntario("Wania Kelly", PATH_IMAGES+"wania.png", "wania@patasfelizes.com.br","85997654398"));       
    }
}
