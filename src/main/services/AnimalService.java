package main.services;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.factories.StatusAnimalFactory;
import main.model.Animal;
import main.repositories.AnimalRepository;
import static main.utils.DateHelper.ConvertMesAnoToCalendar;
import main.utils.ToogleEnum;

public class AnimalService  {

    AnimalRepository animalRepository;    
    ProcedimentoService procedimentoService;
    
    public AnimalService(){
        animalRepository = new AnimalRepository();
        procedimentoService = new ProcedimentoService();
    }
    
    public Animal Salvar(int idAnimal, String nomeAnimal, String anosAnimal, String mesesAnimal, String descricaoAnimal, ToogleEnum sexoAnimal, ToogleEnum castradoAnimal, byte[] fotoAnimal, String ultimoStatus) {
        
        Calendar idade = ConvertMesAnoToCalendar(anosAnimal, mesesAnimal);
        char sexo = sexoAnimal == null ? 'N' : sexoAnimal == ToogleEnum.DIREITO ? 'M' : 'F';
        boolean castrado = castradoAnimal == ToogleEnum.DIREITO;
        String statusAnimal = StatusAnimalFactory.GetStatus(ultimoStatus).name();
        try {
            return  animalRepository.Salvar(idAnimal, nomeAnimal, idade, descricaoAnimal, sexo, castrado, fotoAnimal, statusAnimal);
        } catch (Exception ex) {
            Logger.getLogger(AnimalService.class.getName()).log(Level.SEVERE, null, ex);
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
}
