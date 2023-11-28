package main.services;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.App;
import main.enums.MensagemTipo;
import main.factories.StatusAnimalFactory;
import main.model.Animal;
import main.repositories.AnimalRepository;
import static main.utils.DateHelper.ConvertMesAnoToCalendar;
import main.utils.ToogleEnum;

public class AnimalService  {

    AnimalRepository animalRepository;    
    
    public AnimalService(){
        animalRepository = new AnimalRepository();
    }
    
    public Animal Salvar(int idAnimal, String nomeAnimal, String anosAnimal, String mesesAnimal, String descricaoAnimal, ToogleEnum sexoAnimal, ToogleEnum castradoAnimal, byte[] fotoAnimal, String ultimoStatus) {
        
        Calendar idade = ConvertMesAnoToCalendar(anosAnimal, mesesAnimal);
        char sexo = sexoAnimal == null ? 'N' : sexoAnimal == ToogleEnum.DIREITO ? 'M' : 'F';
        boolean castrado = castradoAnimal == ToogleEnum.DIREITO;
        String statusAnimal = StatusAnimalFactory.GetStatus(ultimoStatus).name();
        try {            
            
            return  animalRepository.Salvar(idAnimal, nomeAnimal, idade, descricaoAnimal, sexo, castrado, fotoAnimal, statusAnimal);
   
        } catch (Exception ex) {
            ex.printStackTrace();
            String mensagem = idAnimal == -1 ? "cadastrar" : "atualizar";
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao " + mensagem + " o pet");
        } 
        
        return null;
    } 
    
    public List<Animal> ObterAnimais(){
        return animalRepository.EncontrarAnimais();
    }
    
    public Set<String> ObterNomesAnimais(){
        return animalRepository.EncontrarNomesAnimais();
    }
    
    public Animal ObterAnimalPorNome(String nome){
        return animalRepository.EncontrarAnimalPorNome(nome);
    }

    public boolean DeletarAnimalPorId(int id) {
        try {
            animalRepository.Excluir(Animal.class, id);
            return true;
        } catch (Exception ex) {          
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao deletar o pet");
            return false;
        }
    }
    
        
    public List<Animal> selecionarAnimais(String ordenacao, String status, boolean filtrarMasculino,
        boolean filtrarFeminino,boolean filtrarSim, boolean filtrarNao, Calendar intervaloPrimeiro, Calendar intervaloSegundo) {
        try {
            return animalRepository.selecionarAnimais(ordenacao, status,
                    filtrarMasculino, filtrarFeminino, filtrarSim, filtrarNao, intervaloPrimeiro, intervaloSegundo);
        } catch (SQLException ex) {
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao filtrar os pets");
            return null;
        }
        
    }
}
