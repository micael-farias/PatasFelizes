package main.model;

import java.util.Calendar;

public class ProcedimentoDto {
    
    private int Id;
    private String Descricao;
    private Animal Animal;
    private Calendar Data;
    private String tipo;
    private double valorDespesa;
    private boolean Realizado;
    public Calendar DataCadastro;
    private String nomeAnimal;
    private String voluntario;
    /*

    */
    public ProcedimentoDto() {
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

    
    public boolean isRealizado() {
        return Realizado;
    }

    public void setRealizado(boolean Realizado) {
        this.Realizado = Realizado;
    }

    public double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public String getNomeAnimal() {
        return nomeAnimal;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public String getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(String voluntario) {
        this.voluntario = voluntario;
    }
    
    
    
}
