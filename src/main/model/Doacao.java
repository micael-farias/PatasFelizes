/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.util.Calendar;
import java.util.Date;
import main.annotations.TableName;

/**
 *
 * @author micha
 */
@TableName("Doacoes")
public class Doacao {

    private int Id;
    private String Doador;
    private double Valor;
    private Calendar Data;
    private byte[] FotoComprovante;
    private Calendar DataCadastro;

    public Doacao(int id, String Doador, double Valor, Calendar Data) {
        this.Id = id;
        this.Doador = Doador;
        this.Valor = Valor;
        this.Data = Data;
        this.DataCadastro =  Calendar.getInstance();
    }

    public Doacao() {
        this.DataCadastro =  Calendar.getInstance();
    }
    public Calendar getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Calendar DataCadastro) {
        this.DataCadastro = DataCadastro;
    }

    public byte[] getFotoComprovante() {
        return FotoComprovante;
    }

    public void setFotoComprovante(byte[] FotoComprovante) {
        this.FotoComprovante = FotoComprovante;
    }
    
    
    
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
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

    public Calendar getData() {
        return Data;
    }

    public void setData(Calendar Data) {
        this.Data = Data;
    }
    
    

   
    
}
