package main.services;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import main.model.Doacao;
import main.repositories.DoacaoRepository;
import static main.utils.DateHelper.LocalDateParaCalendar;
import static main.utils.DateHelper.LocalDateParaDate;
import main.utils.NumberHelper;

public class DoacaoServices {
    DoacaoRepository doacaoRepository;
    
    public DoacaoServices(){
        doacaoRepository = new DoacaoRepository();
    }
    
    public List<Doacao> ObterDoacoes(){
        return doacaoRepository.ObterDoacoes();
    }   

    public Set<String> ObterDoadores() {
        return doacaoRepository.ObterNomesDoadores();
    }
    
    public Doacao Salvar(int idDoacao, String doador, String valorString, LocalDate dataLocal, byte[] fotoComprovante){
        Calendar data = LocalDateParaCalendar(dataLocal);     
        double valor = NumberHelper.DoubleParse(valorString);        
        return doacaoRepository.Salvar(idDoacao, doador, valor, data, fotoComprovante);
    }
}