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
public class Despesa {
       
    private static int nextId = 1;
    private int Id;
    private String Descricao;
    private double Valor;
    private Date Data;
    private Date DataCadastro;
    private String Tipo;

    public Despesa(String Descricao, double Valor, Date Data) {
        this.Id = nextId++;
        this.Descricao = Descricao;
        this.Valor = Valor;
        this.Data = Data;
        this.DataCadastro = new Date();
    }

    public Despesa(String Descricao, double Valor, Date Data, String Tipo) {
        this.Id = nextId++;
        this.Descricao = Descricao;
        this.Valor = Valor;
        this.Data = Data;
        this.Tipo = Tipo;
        this.DataCadastro = new Date();
    }
    
    

    public Despesa() {
        this.Id = nextId++;
        this.DataCadastro = new Date();
    }

    public String getTipo() {
        return Tipo;
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

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
    }

    public int getId() {
        return Id;
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
    
    
    
    
}
