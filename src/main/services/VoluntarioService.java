/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.services;

import java.util.List;
import main.model.Voluntario;
import main.repositories.VoluntarioRepository;

/**
 *
 * @author micha
 */
public class VoluntarioService {
    
    private VoluntarioRepository voluntarioRepository;
    
    public VoluntarioService(){
         voluntarioRepository = new VoluntarioRepository();
    }

    public void Salvar(int idVoluntario, String nome, String email, String telefone) {
        voluntarioRepository.Salvar(idVoluntario, nome, email, telefone);
    }

    public List<Voluntario> ObterVoluntarios() {
        return voluntarioRepository.ObterVoluntarios();
    }
    
}
