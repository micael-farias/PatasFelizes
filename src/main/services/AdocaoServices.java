package main.services;

import java.sql.SQLException;
import main.App;
import main.enums.MensagemTipo;
import main.model.Adocao;
import main.model.Adotante;
import main.repositories.AdocaoRepository;
import main.repositories.AdotantesRepository;
import main.repositories.AnimalRepository;

public class AdocaoServices {

    AdotantesRepository adotanteRepository;
    AdocaoRepository adocaoRepository;
    AnimalRepository animalRepository;
    
    public AdocaoServices(){
        adotanteRepository = new AdotantesRepository();
        adocaoRepository = new AdocaoRepository();
        animalRepository = new AnimalRepository();
    }
    
    
    public Adocao Salvar(int idAdocao, int idAdotante, String tutor, String telefone, String cep, String rua, String cidade, String bairro, String numero, String complemento, int idAnimal){
        
        
        Adocao adocao = null;
        try{
            adocaoRepository.BeginTransaction();
           
            Adotante adotante = adotanteRepository.SalvarAdotante(idAdotante,tutor, telefone, cep, cidade, rua, bairro, numero, complemento);
            
            adocao = adocaoRepository.SalvarAdocao(idAdocao, idAnimal, adotante);
                
            animalRepository.AtualizarStatusAnimal("A", idAnimal);

            
            adocaoRepository.CommitTransaction();
        }catch(Exception e){
            e.printStackTrace();
            String mensagem = idAnimal == -1 ? "cadastrar" : "atualizar";
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao " + mensagem + " a adoção", null);
            try {
                adocaoRepository.RollbackTransaction();
            } catch (SQLException ex) {
                
            }
            adocao = null;
        }
        
               
        return adocao;
    }
    
    
    public Adocao EncontrarAdocaoPorId(int idAnimal){
        return adocaoRepository.EncontrarAdocaoPorAnimal(idAnimal);
    
    }

    public void DeletarAdocaoPorId(int id, int idAnimal) {
        try {
            adocaoRepository.DeletarAdocaoPorId(id);
            animalRepository.AtualizarStatusAnimal("PA", idAnimal);
        } catch (Exception ex) {
                        ex.printStackTrace();

            ex.printStackTrace();
        }
    }
}
