/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.services;

import java.util.List;
import java.util.Set;
import main.model.Voluntario;
import main.repositories.VoluntarioRepository;
import java.util.Calendar;

/**
 *
 * @author micha
 */
public class VoluntarioService {
    
    private VoluntarioRepository voluntarioRepository;
    
    public VoluntarioService(){
         voluntarioRepository = new VoluntarioRepository();
    }

    public void Salvar(int idVoluntario, String nome, String email, String telefone, byte[] fotoVoluntario) {
        voluntarioRepository.Salvar(idVoluntario, nome, email, telefone, fotoVoluntario);
    }

    public List<Voluntario> ObterVoluntarios() {
        return voluntarioRepository.ObterVoluntarios();
    }
 
    public Set<String> ObterNomeVoluntarios() {
        return voluntarioRepository.EncontrarNomesVoluntarios();
    }

    public Voluntario ObterVoluntarioPorNome(String voluntarioString) {
        return voluntarioRepository.EncontrarVoluntarioPor(voluntarioString);
    }
     
}
