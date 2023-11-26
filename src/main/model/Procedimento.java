package main.model;

import java.util.Calendar;
import java.util.Date;

public class Procedimento {
    
    private int Id;
    private String Descricao;
    private Animal Animal;
    private Calendar Data;
    private String tipo;
    private Voluntario Voluntario;
    private Despesa Despesa;
    private Calendar DataCadastro;
    private boolean Realizado;
    
    public Procedimento() {
        this.DataCadastro = Calendar.getInstance();
    }
    
    public int getId() {
        return Id;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
    
    public void setId(int Id) {
        this.Id = Id;
    }
    
    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public Calendar getData() {
        return Data;
    }

    public void setData(Calendar Data) {
        this.Data = Data;
    }

    public Calendar getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Calendar DataCadastro) {
        this.DataCadastro = DataCadastro;
    }

    
    
    public Animal getAnimal() {
        return Animal;
    }

    public void setAnimal(Animal Animal) {
        this.Animal = Animal;
    }

    public Voluntario getVoluntario() {
        return Voluntario;
    }

    public void setVoluntario(Voluntario Voluntario) {
        this.Voluntario = Voluntario;
    }

    public Despesa getDespesa() {
        return Despesa;
    }

    public void setDespesa(Despesa Despesa) {
        this.Despesa = Despesa;
    }

    public boolean isRealizado() {
        return Realizado;
    }

    public void setRealizado(boolean Realizado) {
        this.Realizado = Realizado;
    }
    
    
    
}
