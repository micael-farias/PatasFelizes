package main.model;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class Despesa {
       
    private int Id;
    private String Descricao;
    private double Valor;
    private Calendar Data;
    private String Tipo;
    private boolean Realizada;
    private Calendar DataCadastro;
    private byte[] FotoComprovante;

    public Despesa(String Descricao, double Valor, Calendar Data) {
        this.Descricao = Descricao;
        this.Valor = Valor;
        this.Data = Data;
        this.DataCadastro = Calendar.getInstance();
    }

    public Despesa() {
        this.DataCadastro = Calendar.getInstance();
    }

    public byte[] getFotoComprovante() {
        return FotoComprovante;
    }

    public void setFotoComprovante(byte[] FotoComprovante) {
        this.FotoComprovante = FotoComprovante;
    }

//    
    
    public String getTipo() {
        return Tipo;
    }

    public boolean isRealizada() {
        return Realizada;
    }

    public void setRealizada(boolean Realizada) {
        this.Realizada = Realizada;
    }
    
    

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
    
    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double Valor) {
        this.Valor = Valor;
    }

    public Calendar getData() {
        return Data;
    }

    public void setData(Calendar Data) {
        this.Data = Data;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Calendar getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Calendar DataCadastro) {
        this.DataCadastro = DataCadastro;
    }
    
    
    
    
}
