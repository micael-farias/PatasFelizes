package main.model;

public class Idade {
    private int Anos;
    private int Meses;

    public Idade(int Anos, int Meses) {
        this.Anos = Anos;
        this.Meses = Meses;
    }

    public int getAnos() {
        return Anos;
    }

    public void setAnos(int Anos) {
        this.Anos = Anos;
    }

    public int getMeses() {
        return Meses;
    }

    public void setMeses(int Meses) {
        this.Meses = Meses;
    }   
}
