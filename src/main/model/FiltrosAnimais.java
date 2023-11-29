package main.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static main.utils.DateHelper.invalidString;
import main.utils.NumberHelper;
import static main.utils.NumberHelper.IntegerParse;

public class FiltrosAnimais {
        private String ordenacaoSelecionada;
        private String statusSelecionado;
        private boolean filtrarMasculino;
        private boolean filtrarFeminino;
        private boolean filtrarCastradoSim;
        private boolean filtrarCastradoNao;
        private boolean filtrarSexoDesconhecido;
        private String intervaloPrimeiroAno;
        private String intervaloPrimeiroMeses;
        private String intervaloSegundoAno;
        private String intervaloSegundoMeses;

        
        public HashMap<String, String> GetFiltros(){
            HashMap<String, String> map = new HashMap<>();
            
            if(!invalidString(statusSelecionado)){
                map.put("Status", statusSelecionado);
            }
            if(filtrarMasculino){
                map.put("Masculino", "Macho");
            }
            if(filtrarFeminino){
                map.put("Feminino", "Femea");
            }
            if(filtrarCastradoSim){
                map.put("Castrados", "Castrados");
            }
            if(filtrarCastradoNao){
                map.put("Nao Castrados", "Não Castrados");
            }
            if(filtrarSexoDesconhecido){
                map.put("Sexo desconhecido", "Sexo desconhecido");
            }             
             
            
          
                
 int primeiroAno = IntegerParse(intervaloPrimeiroAno);
        int primeiroMeses = IntegerParse(intervaloPrimeiroMeses);
        int segundoAno = IntegerParse(intervaloSegundoAno);
        int segundoMeses = IntegerParse(intervaloSegundoMeses);

        // Criando uma lista para armazenar os componentes da idade
        String componentesIdade = "";

        // Adicionando ao mapa se os valores convertidos são diferentes de zero
        if (primeiroAno != 0) {
            componentesIdade+=(primeiroAno + " anos");
        }

        if (primeiroMeses != 0) {
            componentesIdade+=(" e "+ primeiroMeses + " meses");
        }
        componentesIdade+=" até ";
        if (segundoAno != 0) {
            componentesIdade+=(segundoAno + " anos");
        }

        if (segundoMeses != 0) {
            componentesIdade+=(" e "+ segundoMeses + " meses");
        }

        // Concatenando os componentes da idade
        if (!componentesIdade.isEmpty()) {
            String idadeConcatenada = String.join(" e ", componentesIdade);
            map.put("Idade", idadeConcatenada);
        }            
            
            return map;
        }
        
        // Getters and setters
        public String getOrdenacaoSelecionada() {
            return ordenacaoSelecionada;
        }

        public boolean isFiltrarSexoDesconhecido() {
            return filtrarSexoDesconhecido;
        }

        public void setFiltrarSexoDesconhecido(boolean filtrarSexoDesconhecido) {
            this.filtrarSexoDesconhecido = filtrarSexoDesconhecido;
        }
     
        public void setOrdenacaoSelecionada(String ordenacaoSelecionada) {
            this.ordenacaoSelecionada = ordenacaoSelecionada;
        }

        public String getStatusSelecionado() {
            return statusSelecionado;
        }

        public void setStatusSelecionado(String statusSelecionado) {
            this.statusSelecionado = statusSelecionado;
        }

        public boolean isFiltrarMasculino() {
            return filtrarMasculino;
        }

        public void setFiltrarMasculino(boolean filtrarMasculino) {
            this.filtrarMasculino = filtrarMasculino;
        }

        public boolean isFiltrarFeminino() {
            return filtrarFeminino;
        }

        public void setFiltrarFeminino(boolean filtrarFeminino) {
            this.filtrarFeminino = filtrarFeminino;
        }

        public boolean isFiltrarCastradoSim() {
            return filtrarCastradoSim;
        }

        public void setFiltrarCastradoSim(boolean filtrarCastradoSim) {
            this.filtrarCastradoSim = filtrarCastradoSim;
        }

        public boolean isFiltrarCastradoNao() {
            return filtrarCastradoNao;
        }

        public void setFiltrarCastradoNao(boolean filtrarCastradoNao) {
            this.filtrarCastradoNao = filtrarCastradoNao;
        }

        public String getIntervaloPrimeiroAno() {
            return intervaloPrimeiroAno;
        }

        public void setIntervaloPrimeiroAno(String intervaloPrimeiroAno) {
            this.intervaloPrimeiroAno = intervaloPrimeiroAno;
        }

        public String getIntervaloPrimeiroMeses() {
            return intervaloPrimeiroMeses;
        }

        public void setIntervaloPrimeiroMeses(String intervaloPrimeiroMeses) {
            this.intervaloPrimeiroMeses = intervaloPrimeiroMeses;
        }

        public String getIntervaloSegundoAno() {
            return intervaloSegundoAno;
        }

        public void setIntervaloSegundoAno(String intervaloSegundoAno) {
            this.intervaloSegundoAno = intervaloSegundoAno;
        }

        public String getIntervaloSegundoMeses() {
            return intervaloSegundoMeses;
        }

        public void setIntervaloSegundoMeses(String intervaloSegundoMeses) {
            this.intervaloSegundoMeses = intervaloSegundoMeses;
        }
    }
