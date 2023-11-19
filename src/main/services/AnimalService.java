package main.services;

import java.util.Date;
import java.util.List;
import java.util.Set;
import main.model.Animal;
import main.repositories.AnimalRepository;
import static main.utils.DateHelper.CalculaDtPorMesesEAnos;
import main.utils.ToogleEnum;

public class AnimalService  {

    AnimalRepository animalRepository;    
    ProcedimentoService procedimentoService;
    
    public AnimalService(){
        animalRepository = new AnimalRepository();
        procedimentoService = new ProcedimentoService();
    }
    
    public void Salvar(int idAnimal, String nomeAnimal, String anosAnimal, String mesesAnimal, String descricaoAnimal, ToogleEnum sexoAnimal, ToogleEnum castradoAnimal, byte[] fotoAnimal, String ultimoStatus) {
        
        Date idade = CalculaDtPorMesesEAnos(anosAnimal, mesesAnimal);
        char sexo = sexoAnimal == ToogleEnum.DIREITO ? 'M' : 'F';
        boolean castrado = castradoAnimal == ToogleEnum.DIREITO;
        Animal animal  = animalRepository.Salvar(idAnimal, nomeAnimal, idade, descricaoAnimal, sexo, castrado, fotoAnimal, ultimoStatus);
        
        //procedimentoService.SalvarProcedimentosSemAnimal(animal);
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
