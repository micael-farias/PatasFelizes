/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.util.Date;
import java.util.List;
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
    private Date Data;
    private byte[] FotoComprovante;
    private Date DataCadastro;

    public Doacao() {
        this.DataCadastro =  new Date();
    }
    public Date getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Date DataCadastro) {
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

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
    }
    
      public static double somarValores(List<Doacao> doacoes) {
        double soma = 0;

        for (Doacao doacao : doacoes) {
            soma += doacao.getValor();
        }

        return soma;
    }
    

   
    
}
