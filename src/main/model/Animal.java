package main.model;

public class Animal {
    
    private String Nome;
    private int Idade;
    private String Foto;
    private char Sexo;
    private boolean Castrado;
    private boolean Vermifugado;
    private String Descricao; 

    public Animal( char Sexo, String Nome, int Idade, String Foto, boolean Castrado, boolean Vermifugado, String Descricao) {
        this.Nome = Nome;
        this.Idade = Idade;
        this.Foto = Foto;
        this.Castrado = Castrado;
        this.Vermifugado = Vermifugado;
        this.Descricao = Descricao;
        this.Sexo = Sexo;
    }

    public char getSexo() {
        return Sexo;
    }
    
    

    public boolean isCastrado() {
        return Castrado;
    }

    public void setCastrado(boolean Castrado) {
        this.Castrado = Castrado;
    }

    public boolean isVermifugado() {
        return Vermifugado;
    }

    public void setVermifugado(boolean Vermifugado) {
        this.Vermifugado = Vermifugado;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
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
