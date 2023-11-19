package main.repositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import main.model.Animal;

public class AnimalRepository {
    
    public static List<Animal> animais = new ArrayList<>();
    
    public AnimalRepository(){
        if(animais.size() == 0){
            animais.addAll(getListaDeAnimais());
        }   
    }
    
    public ArrayList<Animal> EncontrarAnimais(){
        ArrayList<Animal> copiaAnimais = new ArrayList<>(animais);
        Collections.sort(copiaAnimais, Comparator.comparing(Animal::getDataCadastro).reversed());
        return copiaAnimais;
    }
    
    public Animal Salvar(int idAnimal, String nomeAnimal, Date dataNascimentoAnimal, String descricaoAnimal, char sexoAnimal, boolean castrado, byte[] fotoAnimal, String status) {
        int index = EncontrarPosicaoAnimalPorId(idAnimal); 
        Animal animal;
        
        if(index == -1){
            animal = new Animal();
            animal.setNome(nomeAnimal);
            animal.setDataNascimento(dataNascimentoAnimal);
            animal.setDescricao(descricaoAnimal);
            animal.setCastrado(castrado);
            animal.setFoto(fotoAnimal);
            animal.setStatus(status);           
            animais.add(animal); 
        }else{
            animal = animais.get(index);
            animal.setNome(nomeAnimal);
            animal.setDataNascimento(dataNascimentoAnimal);
            animal.setDescricao(descricaoAnimal);
            animal.setCastrado(castrado);
            animal.setFoto(fotoAnimal == null ? animal.getFoto() : fotoAnimal);
            animal.setStatus(status);      
            animais.set(index, animal);
        }   
        
        return animal;
    }
    
    public static List<Animal> getListaDeAnimais() {
        List<Animal> listaAnimais = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            listaAnimais.add(new Animal('F', "Agnetha", sdf.parse("2019-01-01"), "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."));
            listaAnimais.add(new Animal('F', "Frida", sdf.parse("2018-01-01"), "https://i.pinimg.com/736x/4c/17/b6/4c17b655059d174b25bbea9534f7f7a2.jpg", true, false, "Frida, a gata pop icônica, é uma artista talentosa que pinta com suas patinhas. Seu estilo ousado e sua personalidade única fazem dela uma sensação nas redes sociais. Ela adora dançar ao som de sua própria música."));
            listaAnimais.add(new Animal('M', "Elvis", sdf.parse("2021-03-15"), "https://link.estadao.com.br/blogs/nhom/wp-content/uploads/sites/374/2010/08/32236_540.jpg", true, false, "Elvis é o rei dos animais, com seu charme e voz potente que faz todos balançarem o rabinho. Ele adora fazer shows de rock'n'roll para sua plateia felina."));
            listaAnimais.add(new Animal('F', "Madonna", sdf.parse("2017-05-20"), "https://3.bp.blogspot.com/-Nxs4X3R9sp8/WuJ-mM2xr3I/AAAAAAAADFA/KnJGbS_Fk3UQkSlRie8t13JXthuXbCooQCLcBGAs/s1600/gatinha%2Bm%25C3%25A1scara%2Bde%2Bc%25C3%25ADlios.jpg", false, true, "Madonna, a gata fashionista, é conhecida por seu estilo extravagante e dança inigualável. Ela é a rainha da pista de dança felina."));
            listaAnimais.add(new Animal('M', "Bowie", sdf.parse("2019-11-10"), "https://i.pinimg.com/236x/b7/d8/fb/b7d8fb0721bcc8bc62769415627a69f0--evil-cats-metals.jpg", false, false, "Bowie, o gato camaleão, muda de personalidade e aparência constantemente. Ele é um artista versátil e sempre surpreende seus fãs."));
            listaAnimais.add(new Animal('M', "MJ", sdf.parse("2018-08-30"), "https://down-br.img.susercontent.com/file/7a9a8e2cdd5be2a7ec0925e1b8088a30", true, true, "MJ, o mestre do moonwalk felino, é famoso por seus passos de dança incríveis. Ele é o verdadeiro Rei do Pop entre os animais."));
            listaAnimais.add(new Animal('F', "Taylor", sdf.parse("2020-02-14"), "https://www.estadao.com.br/resizer/9fQOKku2lUrFuW7Sv-8xoq7j4WE=/1200x900/filters:format(jpg):quality(80):focal(525x442:535x452)/cloudfront-us-east-1.images.arcpublishing.com/estadao/K7J4QCXBXRGIFEA2ZWRSL4BMFU.jpg", true, false, "Taylor, a gata da música country, tem um coração do tamanho do Texas. Ela canta sobre histórias de amor e aventura."));
            listaAnimais.add(new Animal('F', "Whitney", sdf.parse("2017-09-05"), "https://i.ytimg.com/vi/rUPwopV6bUg/maxresdefault.jpg", false, true, "Whitney, a diva da balada felina, tem uma voz poderosa que faz todos se emocionarem. Ela é a estrela das noites estreladas."));
            listaAnimais.add(new Animal('M', "Elton", sdf.parse("2016-12-01"), "https://img.freepik.com/fotos-premium/gato-de-estimacao-na-parada-do-orgulho-gay-conceito-de-orgulho-lgbtq-gerado-por-ia_878453-725.jpg", true, false, "Elton, o gato extravagante, adora se apresentar em shows glamourosos. Ele é o mestre das teclas e dos óculos de sol."));
            listaAnimais.add(new Animal('M', "Stevie", sdf.parse("2018-05-22"), "https://www.estadao.com.br/resizer/cFN_ZLg3Oek2TXhYD9Dab8rw1iA=/1200x1200/filters:format(jpg):quality(80):focal(-5x-5:5x5)/cloudfront-us-east-1.images.arcpublishing.com/estadao/WWGWQV3MXNPJBFC7TYF73Y5FGY.jpg", false, true, "Stevie, o gato do blues, toca sua guitarra com alma e sentimento. Ele transforma suas experiências em músicas emotivas."));

            // Exemplo de como acessar os dados do primeiro animal
            Animal primeiroAnimal = listaAnimais.get(0);
            System.out.println("Nome do primeiro animal: " + primeiroAnimal.getNome());
        } catch (ParseException e) {
            e.printStackTrace();
        }
     
        return FXCollections.observableArrayList(listaAnimais);
    } 

    public Animal EncontrarAnimalPor(int idAnimal) {
         for(Animal a : animais){
             if(a.getId() == idAnimal){
                 return a;
             }
         }
         
         return null;
    }
    
    public int EncontrarPosicaoAnimalPorId(int idAnimal) {
         for(Animal a : animais){
             if(a.getId() == idAnimal){
                 return animais.indexOf(a);
             }
         }
         
         return -1;
    }

    public Animal EncontrarAnimalPorNome(String nome) {
        for(Animal a : animais){
            if(a.getNome().equals(nome)){
                 return a;
            }
        }
        
        return null;
    }

    public Set<String> EncontrarNomesAnimais() {     
        Set<String> nomesAnimais = new HashSet<>();
        for(Animal a : animais){
             nomesAnimais.add(a.getNome());
        }
        return nomesAnimais; 
    }
    
}
