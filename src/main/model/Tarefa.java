/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.util.Calendar;
import java.util.Date;
import main.interfaces.PossuiIdFoto;

/**
 *
 * @author micha
 */
public class Tarefa{

    private int Id;
    private Voluntario Voluntário;
    private Animal Animal; 
    private String Descricao;
    private Calendar Data;
    private Calendar DataCadastro;
    private String tipo;
    private boolean Realizado;
 
    public Calendar getDataCadastro() {
        return DataCadastro;
    }     

    public Tarefa(){
        this.DataCadastro = Calendar.getInstance();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Voluntario getVoluntario() {
        if(Voluntário == null){
            return new Voluntario();
        }
        return Voluntário;
    }

    public void setVoluntario(Voluntario Voluntário) {
        this.Voluntário = Voluntário;
    }

    public Animal getAnimal() {
        return Animal;
    }

    public void setAnimal(Animal Animal) {
        this.Animal = Animal;
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
    public boolean isRealizada() {
        return Realizado;
    }

    public void setRealizado(boolean Realizado) {
        this.Realizado = Realizado;
    }
    
}
