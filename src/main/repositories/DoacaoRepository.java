package main.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import main.model.Doacao;

public class DoacaoRepository {
    
    public static List<Doacao> doacoes = new ArrayList<>();
    
    public DoacaoRepository(){
        if(doacoes.size() == 0) a();
    }
    
    public void a(){ 
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
        doacoes.add(new Doacao(1, "Fulano da Silva", 100.0, new Date()));
    }

    public List<Doacao> ObterDespesas() {
        return new ArrayList<>(doacoes);
    }
}
