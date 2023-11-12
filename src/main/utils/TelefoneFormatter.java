/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.utils;

/**
 *
 * @author micha
 */
public class TelefoneFormatter {
     
    public static String FormatarTelefone(String numero) {
        String apenasDigitos = numero.replaceAll("[^0-9]", "");

        // Verificar o comprimento do número
        switch (apenasDigitos.length()) {
            case 11:
                // (XX) XXXXX-XXXX
                return String.format("(%s) %s-%s",
                        apenasDigitos.substring(0, 2),
                        apenasDigitos.substring(2, 7),
                        apenasDigitos.substring(7));
            case 10:
                // (XX) XXXX-XXXX
                return String.format("(%s) %s-%s",
                        apenasDigitos.substring(0, 2),
                        apenasDigitos.substring(2, 6),
                        apenasDigitos.substring(6));
            default:
                return "Número de telefone inválido";
        }
    }
}
