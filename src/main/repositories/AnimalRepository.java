package main.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.model.Animal;
import static main.utils.DateHelper.DateToCalendar;

public class AnimalRepository extends BaseRepository<Animal>{
    
    
    public AnimalRepository(){
        super(Animal.class);    
                        
    }
    
    public ArrayList<Animal> EncontrarAnimais(){
       var a =  new ArrayList<>(SelecionarTodos("*", null, "DataCadastro desc", Animal.class ));
       return a;
    }
    
    public Animal Salvar(int idAnimal, String nomeAnimal, Calendar dataNascimentoAnimal, String descricaoAnimal, char sexoAnimal, boolean castrado, byte[] fotoAnimal, String status) throws SQLException, IllegalAccessException {
        Animal animal;
        
        if(idAnimal == -1){
            animal = new Animal();
            animal.setNome(nomeAnimal);
            animal.setDataNascimento(dataNascimentoAnimal);
            animal.setDescricao(descricaoAnimal);
            animal.setCastrado(castrado);
            animal.setFoto(fotoAnimal);
            animal.setStatus(status);           
            animal.setSexo(sexoAnimal);
            Inserir(animal);          
        }else{
            animal = EncontrarAnimalPor(idAnimal);
            animal.setNome(nomeAnimal);
            System.out.println(dataNascimentoAnimal.getTimeInMillis());
            animal.setDataNascimento(dataNascimentoAnimal);
            animal.setDescricao(descricaoAnimal);
            animal.setCastrado(castrado);
            animal.setFoto(fotoAnimal == null ? animal.getFoto() : fotoAnimal);
            animal.setStatus(status);     
            animal.setSexo(sexoAnimal);
            Atualizar(animal);
        }   
        
        return animal;
    }
     private static Calendar criarCalendar(int ano, int mes, int dia) {
        return new GregorianCalendar(ano, mes, dia);
    }  
    public static List<Animal> getListaDeAnimais() {
        List<Animal> listaAnimais = new ArrayList<>();

          
        listaAnimais.add(new Animal('F', "Agnetha", criarCalendar(2019, 0, 1), "https://blog-static.petlove.com.br/wp-content/uploads/2021/08/munchkin-branco-Petlove.jpg", true, true, "Agnetha, a estrela pop brilhante, é uma gata graciosa que encanta a todos com seu charme. Sua voz suave é como música para os ouvidos e seu pelo é tão macio quanto o veludo. Ela é uma diva no mundo felino."));
        listaAnimais.add(new Animal('F', "Frida", criarCalendar(2018, 0, 1), "https://i.pinimg.com/736x/4c/17/b6/4c17b655059d174b25bbea9534f7f7a2.jpg", true, false, "Frida, a gata pop icônica, é uma artista talentosa que pinta com suas patinhas. Seu estilo ousado e sua personalidade única fazem dela uma sensação nas redes sociais. Ela adora dançar ao som de sua própria música."));
        listaAnimais.add(new Animal('M', "Elvis", criarCalendar(2021, 2, 15), "https://link.estadao.com.br/blogs/nhom/wp-content/uploads/sites/374/2010/08/32236_540.jpg", true, false, "Elvis é o rei dos animais, com seu charme e voz potente que faz todos balançarem o rabinho. Ele adora fazer shows de rock'n'roll para sua plateia felina."));
        listaAnimais.add(new Animal('F', "Madonna", criarCalendar(2017, 4, 20), "https://3.bp.blogspot.com/-Nxs4X3R9sp8/WuJ-mM2xr3I/AAAAAAAADFA/KnJGbS_Fk3UQkSlRie8t13JXthuXbCooQCLcBGAs/s1600/gatinha%2Bm%25C3%25A1scara%2Bde%2Bc%25C3%25ADlios.jpg", false, true, "Madonna, a gata fashionista, é conhecida por seu estilo extravagante e dança inigualável. Ela é a rainha da pista de dança felina."));
        listaAnimais.add(new Animal('M', "Bowie", criarCalendar(2019, 10, 10), "https://i.pinimg.com/236x/b7/d8/fb/b7d8fb0721bcc8bc62769415627a69f0--evil-cats-metals.jpg", false, false, "Bowie, o gato camaleão, muda de personalidade e aparência constantemente. Ele é um artista versátil e sempre surpreende seus fãs."));
        listaAnimais.add(new Animal('M', "MJ", criarCalendar(2018, 7, 30), "https://down-br.img.susercontent.com/file/7a9a8e2cdd5be2a7ec0925e1b8088a30", true, true, "MJ, o mestre do moonwalk felino, é famoso por seus passos de dança incríveis. Ele é o verdadeiro Rei do Pop entre os animais."));
        listaAnimais.add(new Animal('F', "Taylor", criarCalendar(2020, 1, 14), "https://www.estadao.com.br/resizer/9fQOKku2lUrFuW7Sv-8xoq7j4WE=/1200x900/filters:format(jpg):quality(80):focal(525x442:535x452)/cloudfront-us-east-1.images.arcpublishing.com/estadao/K7J4QCXBXRGIFEA2ZWRSL4BMFU.jpg", true, false, "Taylor, a gata da música country, tem um coração do tamanho do Texas. Ela canta sobre histórias de amor e aventura."));
        listaAnimais.add(new Animal('F', "Whitney", criarCalendar(2017, 8, 5), "https://i.ytimg.com/vi/rUPwopV6bUg/maxresdefault.jpg", false, true, "Whitney, a diva da balada felina, tem uma voz poderosa que faz todos se emocionarem. Ela é a estrela das noites estreladas."));
        listaAnimais.add(new Animal('M', "Elton", criarCalendar(2016, 11, 1), "https://img.freepik.com/fotos-premium/gato-de-estimacao-na-parada-do-orgulho-gay-conceito-de-orgulho-lgbtq-gerado-por-ia_878453-725.jpg", true, false, "Elton, o gato extravagante, adora se apresentar em shows glamourosos. Ele é o mestre das teclas e dos óculos de sol."));
        listaAnimais.add(new Animal('M', "Stevie", criarCalendar(2018, 4, 22), "https://www.estadao.com.br/resizer/cFN_ZLg3Oek2TXhYD9Dab8rw1iA=/1200x1200/filters:format(jpg):quality(80):focal(-5x-5:5x5)/cloudfront-us-east-1.images.arcpublishing.com/estadao/WWGWQV3MXNPJBFC7TYF73Y5FGY.jpg", false, true, "Stevie, o gato do blues, toca sua guitarra com alma e sentimento. Ele transforma suas experiências em músicas emotivas."));

        // Exemplo de como acessar os dados do primeiro animal
        Animal primeiroAnimal = listaAnimais.get(0);
        System.out.println("Nome do primeiro animal: " + primeiroAnimal.getNome());

        return listaAnimais;
         } 


    
     public Animal EncontrarAnimalPor(int id) {
        String sql = "SELECT * FROM Animais WHERE Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearAnimal(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Animal mapearAnimal(ResultSet resultSet) throws SQLException {
        Animal animal = new Animal();
        animal.setId(resultSet.getInt("Id"));
        animal.setNome(resultSet.getString("Nome"));
        animal.setDataNascimento(DateToCalendar(resultSet.getTimestamp("DataNascimento")));
        animal.setFoto(resultSet.getBytes("Foto"));
        animal.setDescricao(resultSet.getString("Descricao"));
        animal.setSexo(resultSet.getString("Sexo").charAt(0));
        animal.setCastrado(resultSet.getBoolean("Castrado"));
        animal.setStatus(resultSet.getString("Status"));
        animal.setDataCadastro(DateToCalendar(resultSet.getTimestamp("DataCadastro")));

        return animal;
    }
    
    public Animal EncontrarAnimalPorNome(String nome) {    
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryBuscaPorNome("Animais",nome))) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Animal encontrado = mapearAnimal(resultSet);

                    // Verificar se há mais de uma correspondência
                    if (resultSet.next()) {
                        return null;
                    }

                    return encontrado;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set<String> EncontrarNomesAnimais() {     
        return new HashSet<String>(SelecionarTodos("NOME",null,null, String.class));
    }

    public List<Animal> EncontrarAnimaisPorNome(String nome) {
        return SelecionarTodos("*", "NOME LIKE '%"+nome+"%'",null, Animal.class);
    }

    public void AtualizarStatusAnimal(String status, int idAnimal) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Animais SET status=? WHERE id=?")) {

            statement.setString(1, status);
            statement.setInt(2, idAnimal);
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected != 1) {
                throw new Exception("Falha na atualização do Status do animal");
            }
        } catch (SQLException e) {
            throw e;
        }

    }
    
     public List<Animal> selecionarAnimais(String ordenacao, String status,
            boolean filtrarMasculino, boolean filtrarFeminino,
            boolean filtrarSim, boolean filtrarNao,
            Calendar intervaloPrimeiro, Calendar intervaloSegundo) throws SQLException {
            List<Animal> animais = new ArrayList<>();

        try {

            StringBuilder sql = new StringBuilder("SELECT * FROM Animais WHERE 1 = 1");

            if (status != null && !status.isEmpty()) {
                sql.append(" AND Status = '").append(status).append("'");
            }          
            
            if (filtrarMasculino) {
                if(!filtrarFeminino){
                    sql.append(" AND Sexo = 'M'");
                }               
            }else if(filtrarFeminino){
                sql.append(" AND Sexo = 'F'");
            }
            
            if (filtrarSim) {
                if(!filtrarNao){
                    sql.append(" AND Castrado = 1");
                } 
            }else if(filtrarNao){
                sql.append(" AND CASTRADO = 0");
            }
            
            
            if (intervaloPrimeiro != null && intervaloSegundo != null) {
                sql.append(" AND DataNascimento BETWEEN ? AND ?");
            }
            
            if (ordenacao != null && !ordenacao.isEmpty()) {
                sql.append(" ORDER BY ").append(ordenacao);
            }
            
            System.out.println(sql);
            
            try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                        
                if(intervaloPrimeiro != null && intervaloSegundo != null){
                    statement.setTimestamp(1, new Timestamp(intervaloPrimeiro.getTimeInMillis()));
                    statement.setTimestamp(2, new Timestamp(intervaloSegundo.getTimeInMillis()));                        
                }
       
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Animal animal = mapearAnimal(resultSet);
                        animais.add(animal);
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return animais;
    }
    
    
}
