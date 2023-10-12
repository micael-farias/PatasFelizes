package main.model;

public class Animal {
    
    private String Nome;
    private int Idade;
    private String Foto;

    public Animal(String Nome, int Idade, String Foto) {
        this.Nome = Nome;
        this.Idade = Idade;
        this.Foto = Foto;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public int getIdade() {
        return Idade;
    }

    public void setIdade(int Idade) {
        this.Idade = Idade;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String Foto) {
        this.Foto = Foto;
    }   
}
