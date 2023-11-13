/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.util.Date;

/**
 *
 * @author micha
 */
public class Tarefa {
    private int Id;
    private Voluntario Voluntário;
    private Animal Animal; 
    private String DescricaoTarefa;
    private Date Data;

    public Tarefa(int Id, Voluntario Voluntário, Animal Animal, String DescricaoTarefa, Date Data) {
        this.Id = Id;
        this.Voluntário = Voluntário;
        this.Animal = Animal;
        this.DescricaoTarefa = DescricaoTarefa;
        this.Data = Data;
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

    public String getDescricaoTarefa() {
        return DescricaoTarefa;
    }

    public void setDescricaoTarefa(String DescricaoTarefa) {
        this.DescricaoTarefa = DescricaoTarefa;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
    }

    
  


   
    
}
