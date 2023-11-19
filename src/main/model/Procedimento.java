package main.model;

import java.util.Date;

public class Procedimento {
    private static int nextId = 1;
    
    private int Id;
    private String Descricao;
    private Animal Animal;
    private Date Data;
    private String tipo;
    private Voluntario Voluntario;
    private Despesa Despesa;
    private Date DataCadastro;
    private boolean Realizado;
    
    public Procedimento(String Descricao, Date Data) {
        this.Id = nextId++;
        this.Descricao = Descricao;
        this.Data = Data;
        this.DataCadastro = new Date();
    }

    public Procedimento() {
        this.Id  = nextId++;
        this.DataCadastro = new Date();
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

    public Date getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Date DataCadastro) {
        this.DataCadastro = DataCadastro;
    }
    
    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
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
