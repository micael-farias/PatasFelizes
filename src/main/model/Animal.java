package main.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import main.annotations.TableName;
import main.interfaces.PossuiIdFoto;
import static main.utils.ImageConverter.ConvertUrlToByteArray;


@TableName("Animais")
public class Animal implements PossuiIdFoto{
        
    private int Id;
    private String Nome;
    private Calendar DataNascimento;
    private byte[] Foto;
    private String Descricao; 
    private char Sexo;
    private boolean Castrado;
    private String Status;
    private Calendar DataCadastro;

    public Animal(char Sexo, String Nome, Calendar DataNascimento, String Foto, boolean Castrado, boolean Vermifugado, String Descricao) {
        this.Nome = Nome;
        this.DataNascimento = DataNascimento;
        this.Foto = ConvertUrlToByteArray(Foto);
        this.Castrado = Castrado;
        this.Descricao = Descricao;
        this.Status = "A";
        this.DataCadastro = Calendar.getInstance();
        this.Sexo = Sexo;
    }
    
    public Animal(){
        this.DataCadastro = Calendar.getInstance();
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

    public Calendar getDataCadastro() {
        return DataCadastro;
    }

    public void setSexo(char Sexo) {
        this.Sexo = Sexo;
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

    public Calendar getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
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
