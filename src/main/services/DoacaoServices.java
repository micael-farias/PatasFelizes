/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.services;

import java.util.List;
import main.model.Doacao;
import main.repositories.DoacaoRepository;

/**
 *
 * @author micha
 */
public class DoacaoServices {
    DoacaoRepository doacaoRepository;
    
    public DoacaoServices(){
        doacaoRepository = new DoacaoRepository();
    }
    
    public List<Doacao> ObterDoacoes(){
        return doacaoRepository.ObterDespesas();
    }   
    
     
}
