/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.util.Date;
import main.interfaces.PossuiIdFoto;
import static main.utils.UrlToByteArrayConverter.ConvertUrlToByteArray;

/**
 *
 * @author micha
 */
public class Voluntario implements PossuiIdFoto{
    private static int nextId = 1;
    private int Id;
    private String nome;
    private byte[] foto;
    private String email;
    private String telefone;
    private Date DataCadastro;
    
    public Voluntario(String nome, String foto, String email, String telefone) {
        this.Id = nextId++;
        this.nome = nome;
        this.foto = ConvertUrlToByteArray(foto);
        this.email = email;
        this.telefone = telefone;
        this.DataCadastro = new Date();
    } 
    
    public Voluntario(){
        this.Id = nextId++;
        this.DataCadastro = new Date();
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
    
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    

    @Override
    public String idFoto() {
        return "Voluntario" + getId();
    }

    
}
