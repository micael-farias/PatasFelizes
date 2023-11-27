/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.enums;

import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public class Mapping {
    private static HashMap<String, String> ordenacoes = new HashMap<>(
            Map.of(
                "Mais recente", "DataCadastro Desc",
                "Mais antigo", "DataCadastro Asc",
                "Mais novo", "DataNascimento Desc",
                "Mais velho", "DataNascimento Asc"
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

    public static ObservableList<String> getOrdenacoes() {
        return FXCollections.observableArrayList(ordenacoes.keySet());
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
    
    
    
}
