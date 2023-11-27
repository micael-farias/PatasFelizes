package main.services;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import main.App;
import main.enums.MensagemTipo;
import main.model.Doacao;
import main.repositories.DoacaoRepository;
import static main.utils.DateHelper.LocalDateParaCalendar;

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
    
    public Doacao Salvar(int idDoacao, String doador, double valor, LocalDate dataLocal, byte[] fotoComprovante){
        Calendar data = LocalDateParaCalendar(dataLocal);     
        try {
            return doacaoRepository.Salvar(idDoacao, doador, valor, data, fotoComprovante);
        } catch (Exception ex) {
            ex.printStackTrace();
            String mensagem = idDoacao == -1 ? "cadastrar" : "atualizar";
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao " + mensagem + " a doacao");
                      
            return null;
        } 
    }

    public double[] ObterTotalReceitaEDespesa() {
        return doacaoRepository.BuscarValoresDoacoesEDespesa();
    }

    public List<Doacao> ObterDoacoesPorDoador(String texto) {
        return doacaoRepository.ObterDoacoesPorDescricao(texto);
    }
}
