package main.model;

import java.util.Calendar;

 public class FiltroTarefa{
       
        private String Tipo;
        private String Ordenacao;
        private String Voluntario;
        private String Animal;
        private Calendar DataInicial;
        private Calendar DataFinal;

        public String getTipo() {
            return Tipo;
        }

        public void setTipo(String Tipo) {
            this.Tipo = Tipo;
        }

        public String getOrdenacao() {
            return Ordenacao;
        }

        public void setOrdenacao(String Ordenacao) {
            this.Ordenacao = Ordenacao;
        }

        public String getVoluntario() {
            return Voluntario;
        }

        public void setVoluntario(String Voluntario) {
            this.Voluntario = Voluntario;
        }

        public String getAnimal() {
            return Animal;
        }

        public void setAnimal(String Animal) {
            this.Animal = Animal;
        }

        public Calendar getDataInicial() {
            return DataInicial;
        }

        public void setDataInicial(Calendar DataInicial) {
            this.DataInicial = DataInicial;
        }

        public Calendar getDataFinal() {
            return DataFinal;
        }

        public void setDataFinal(Calendar DataFinal) {
            this.DataFinal = DataFinal;
        }
        
        
        
    }