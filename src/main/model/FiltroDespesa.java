package main.model;

import java.util.Calendar;
import java.util.HashMap;
import main.utils.DateHelper;
import static main.utils.DateHelper.CalendarParaStringReduced;
import static main.utils.DateHelper.invalidString;

 public class FiltroDespesa{
       
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

        public HashMap<String, String> GetFiltros(){
            HashMap<String, String> map = new HashMap<>();
            
            if(!invalidString(Tipo)){
                map.put("Tipo", Tipo);
            }
            if(!invalidString(Voluntario)){
                map.put("Voluntario", Voluntario);
            }
            if(!invalidString(Animal)){
                map.put("Animal", Animal);
            }
            if(DataInicial != null && DataFinal != null){
                map.put("Datas", "De "+ CalendarParaStringReduced(DataInicial) +" para "+ CalendarParaStringReduced(DataFinal));
            }         
             
            
            return map;
        }
        
        
        
    }