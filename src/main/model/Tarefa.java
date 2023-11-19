/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.util.Date;
import main.interfaces.PossuiIdFoto;

/**
 *
 * @author micha
 */
public class Tarefa{
    private static int nextId = 1;
    private int Id;
    private Voluntario Voluntário;
    private Animal Animal; 
    private String Descricao;
    private Date Data;
    private Date DataCadastro;

    public Tarefa(Voluntario Voluntário, Animal Animal, String DescricaoTarefa, Date Data) {
        this.Id = nextId++;
        this.Voluntário = Voluntário;
        this.Animal = Animal;
        this.Descricao = DescricaoTarefa;
        this.Data = Data;
        this.DataCadastro = new Date();
    }

    public Date getDataCadastro() {
        return DataCadastro;
    }     

    public Tarefa(){
        this.Id = nextId++;
        this.DataCadastro = new Date();
    }
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Voluntario getVoluntário() {
        return Voluntário;
    }

    public void setVoluntário(Voluntario Voluntário) {
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

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
    }
}
