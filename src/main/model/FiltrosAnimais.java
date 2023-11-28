package main.model;

public class FiltrosAnimais {
        private String ordenacaoSelecionada;
        private String statusSelecionado;
        private boolean filtrarMasculino;
        private boolean filtrarFeminino;
        private boolean filtrarCastradoSim;
        private boolean filtrarCastradoNao;
        private String intervaloPrimeiroAno;
        private String intervaloPrimeiroMeses;
        private String intervaloSegundoAno;
        private String intervaloSegundoMeses;

        // Getters and setters
        public String getOrdenacaoSelecionada() {
            return ordenacaoSelecionada;
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
