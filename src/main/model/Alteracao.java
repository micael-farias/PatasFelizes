package main.model;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static main.utils.DateHelper.CalendarParaStringReduced;

public class Alteracao {
    private int Id;
    private String TabelaAfetada;
    private String Descritor;
    private int IdRegistroAfetado;
    private String ColunaAlterada;
    private String ValorAntigo;
    private String ValorNovo;
    private Calendar DataAlteracao;
    
    private String Mensagem;
    private String Identificador;

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String Mensagem) {
        this.Mensagem = Mensagem;
    }

    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String Identificador) {
        this.Identificador = Identificador;
    }    
        
    public int getId() {
        return Id;
    }

    public String getTabelaAfetada() {
        return TabelaAfetada;
    }

    public int getIdRegistroAfetado() {
        return IdRegistroAfetado;
    }

    public String getColunaAlterada() {
        return ColunaAlterada;
    }

    public String getValorAntigo() {
        return ValorAntigo;
    }

    public String getValorNovo() {
        return ValorNovo;
    }

    public Calendar getDataAlteracao() {
        return DataAlteracao;
    }

    public String getDescritor() {
        return Descritor;
    }

    public void setDescritor(String Descritor) {
        this.Descritor = Descritor;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setTabelaAfetada(String TabelaAfetada) {
        this.TabelaAfetada = TabelaAfetada;
    }

    public void setIdRegistroAfetado(int IdRegistroAfetado) {
        this.IdRegistroAfetado = IdRegistroAfetado;
    }

    public void setColunaAlterada(String ColunaAlterada) {
        this.ColunaAlterada = ColunaAlterada;
    }

    public void setValorAntigo(String ValorAntigo) {
        this.ValorAntigo = ValorAntigo;
    }

    public void setValorNovo(String ValorNovo) {
        this.ValorNovo = ValorNovo;
    }

    public void setDataAlteracao(Calendar DataAlteracao) {
        this.DataAlteracao = DataAlteracao;
    }

    

    // Método para formatar a mensagem
    public void formatarMensagem() {
        this.Mensagem = String.format(
                "Sua/Seu %s alterada de \"%s\" para \"%s\" em %s.",
                ColunaAlterada.toLowerCase(),
                ValorAntigo,
                ValorNovo,
                CalendarParaStringReduced(DataAlteracao));
        
        this.Identificador =  String.format("%s descrito por %s foi alterado",transformarParaSingular(TabelaAfetada), Descritor);      
       
    }

    private String transformarParaSingular(String palavraPlural) {
        switch (palavraPlural.toLowerCase()) {
            case "animais":
                return "O animal";
            case "doacoes":
                return "A doação";
            case "voluntarios":
                return "O voluntário";
            case "despesas":
                return "A despesa";
            case "tarefas":
                return "A tarefa";
            case "procedimentos":
                return "O procedimento";
            case "adotantes":
                return "O adotante";
            case "alteracoes":
                return "A alteracao";
            default:
                return null; // Ou lance uma exceção, dependendo dos requisitos do seu aplicativo.
        }
    }

  
}
