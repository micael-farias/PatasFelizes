package main.services;

import java.util.List;
import main.model.Alteracao;
import main.model.Tarefa;
import main.repositories.AlteracoesRepository;

public class AlteracoesServices {

    AlteracoesRepository alteracoesRepository;
    
    public AlteracoesServices(){
        alteracoesRepository = new AlteracoesRepository();
    }
    
    public List<Alteracao> ObterAlteracoes(){
        return alteracoesRepository.EncontrarAlteracoes();
    }    

    public List<Alteracao> EncontrarAlteracoesPorDescricao(String alteracoes) {
        return alteracoesRepository.EncontrarAlteracoesPorDescricao(alteracoes);
    }
    
}
