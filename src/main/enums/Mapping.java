/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public class Mapping {
    private static HashMap<String, String> ordenacoes = new HashMap<>(
            Map.of(
                "Mais recente (Data de cadastro)", "DataCadastro Desc",
                "Mais antigo (Data de cadastro)", "DataCadastro Asc",
                "Mais novo", "DataNascimento Desc",
                "Mais velho", "DataNascimento Asc"
            )
    );
    
    private static HashMap<String, String> ordenacoesDespesaDoacoes = new HashMap<>(
            Map.of(
                "Mais recente (Data de cadastro)", "DataCadastro Desc",
                "Mais antigo (Data de cadastro)", "DataCadastro Asc",
                "Mais novo", "Data Desc",
                "Mais velho", "Data Asc",
                "Maior valor", "Valor Desc",
                "Menor valor", "Valor Asc"
            )
    );
    
    private static HashMap<String, String> status = new HashMap<>(
            Map.of(
                "Para a adoção", "PA",
                "Adotado", "A",
                "Desaparecido", "D",
                "Falecido", "F"
            )
    );

    public static HashMap<String, String> getOrdenacoesDespesaDoacoesHash() {
        return ordenacoesDespesaDoacoes;
    }
        
    public static HashMap<String, String> getOrdenacoesHash() {
        return ordenacoes;
    }
    
    public static ObservableList<String> getOrdenacoes() {
        return FXCollections.observableArrayList(ordenacoes.keySet());
    }
        
    public static ObservableList<String> getOrdenacoesDespesaDoacoes() {
        return FXCollections.observableArrayList(ordenacoesDespesaDoacoes.keySet());
    }
    
    public static ObservableList<String> getStatus() {
        return FXCollections.observableArrayList(status.keySet());
    }
    
    public static String GetKeyOrdenacoes(String key){
        return ordenacoes.get(key);
    }

    public static String GetKeyStatus(String statusSelecionado) {
        return status.get(statusSelecionado);
    }
    
    public static String GetKeyOrdenacoesDespesaDoacoes(String key){
        return ordenacoesDespesaDoacoes.get(key);
    }
    
    public static String GetKeyByValue(HashMap<String,String> map, String value){
        
        for(Entry<String, String> entry: map.entrySet()) {

           // if give value is equal to value from entry
           if(entry.getValue().equals(value)) {
                return entry.getKey();
           } 
        }
        
        return null;
    }
    
}
