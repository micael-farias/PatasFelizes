package main.model;


import java.util.Calendar;
import main.annotations.TableName;

@TableName("Adocoes")
public class Adocao {
    private int id;
    private int idAnimal;
    private Adotante Adotante;
    private Calendar dataCadastro;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Adotante getAdotante() {
        return Adotante;
    }

    public void setAdotante(Adotante Adotante) {
        this.Adotante = Adotante;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    // Other methods if needed
}
