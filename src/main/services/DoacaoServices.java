package main.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.App;
import main.enums.MensagemTipo;
import main.model.Doacao;
import main.model.FiltroDespesa;
import main.model.Procedimento;
import main.repositories.DoacaoRepository;
import static main.utils.DateHelper.LocalDateParaCalendar;
import static main.utils.DateHelper.LocalDateParaDate;

public class DoacaoServices {
    
    DoacaoRepository doacaoRepository;
    public static FiltroDespesa filtro;

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
        Date data = LocalDateParaDate(dataLocal);     
        try {
            Doacao doacao = doacaoRepository.Salvar(idDoacao, doador, valor, data, fotoComprovante);
            return doacao;
        } catch (Exception ex) {
            ex.printStackTrace();
            String mensagem = idDoacao == -1 ? "cadastrar" : "atualizar";
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao " + mensagem + " a doacao", null);
                      
            return null;
        } 
    }

    public double[] ObterTotalReceitaEDespesa() {
        return doacaoRepository.BuscarValoresDoacoesEDespesa();
    }

    public List<Doacao> ObterDoacoesPorDoador(String texto) {
        return doacaoRepository.ObterDoacoesPorDescricao(texto);
    }

    public List<Doacao> FiltrarDoacoes(FiltroDespesa filtro) {
        try {
            return doacaoRepository.FiltrarDoacoes(filtro);
        } catch (SQLException ex) {
             ex.printStackTrace();
             App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao filtrar doacoes", null);
             return null;
        }
    }

    public int Excluir(int id) {
        try {
            doacaoRepository.Excluir(Doacao.class, id);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Falha ao deletar doação", null);
            return 0;
        }
    }
}
