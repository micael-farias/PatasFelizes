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
public class Doacao {
    private int id;
    private String Doador;
    private double Valor;
    private Date Data;


    public Doacao(int id, String Doador, double Valor, Date Data) {
        this.id = id;
        this.Doador = Doador;
        this.Valor = Valor;
        this.Data = Data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoador() {
        return Doador;
    }

    public void setDoador(String Doador) {
        this.Doador = Doador;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double Valor) {
        this.Valor = Valor;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
    }
    
    

   
    
}
