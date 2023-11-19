package main.model;

import java.util.Date;
import main.interfaces.PossuiIdFoto;
import static main.utils.UrlToByteArrayConverter.ConvertUrlToByteArray;

public class Animal implements PossuiIdFoto{
    
    public static int next = 1;
    
    private int Id;
    private String Nome;
    private Date DataNascimento;
    private byte[] Foto;
    private String Descricao; 
    private char Sexo;
    private boolean Castrado;
    private Date DataCadastro;
    private String Status;

    public Animal(char Sexo, String Nome, Date DataNascimento, String Foto, boolean Castrado, boolean Vermifugado, String Descricao) {
        this.Id = next++;
        this.Nome = Nome;
        this.DataNascimento = DataNascimento;
        this.Foto = ConvertUrlToByteArray(Foto);
        this.Castrado = Castrado;
        this.Descricao = Descricao;
        this.Sexo = Sexo;
        this.DataCadastro = new Date();
    }
    
    public Animal(){
        this.Id = next++;
        this.DataCadastro = new Date();
    }

    public static int getNext() {
        return next;
    }

    public static void setNext(int next) {
        Animal.next = next;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    public char getSexo() {
        return Sexo;
    }

    public Date getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Date DataCadastro) {
        this.DataCadastro = DataCadastro;
    }
       
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public boolean isCastrado() {
        return Castrado;
    }

    public void setCastrado(boolean Castrado) {
        this.Castrado = Castrado;
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

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.DataNascimento = dataNascimento;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] Foto) {
        this.Foto = Foto;
    }
    
    @Override
    public String idFoto() {
        return "Animal" + getId();
    }
}
