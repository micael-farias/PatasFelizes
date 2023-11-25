/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model;

import java.util.Calendar;
import main.annotations.TableName;
import main.interfaces.PossuiIdFoto;

@TableName("Voluntarios")
public class Voluntario implements PossuiIdFoto{

    private int Id;
    private String Nome;
    private byte[] Foto;
    private String Email;
    private String Telefone;
    private Calendar DataCadastro;
    
    public Voluntario(){
        this.DataCadastro = Calendar.getInstance();
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    
    
    public Calendar getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Calendar DataCadastro) {
        this.DataCadastro = DataCadastro;
    }
    
    
    
    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] foto) {
        this.Foto = foto;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }
    

    @Override
    public String idFoto() {
        return "Voluntario" + getId();
    }

    
}
