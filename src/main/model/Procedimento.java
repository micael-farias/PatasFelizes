package main.model;

import java.util.Date;

public class Procedimento {
    private String Descricao;
    private Date Data;

    public Procedimento(String Descricao, Date Data) {
        this.Descricao = Descricao;
        this.Data = Data;
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
