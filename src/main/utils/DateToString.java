/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author micha
 */
public class DateToString {
       
   public static String DataParaString(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss");
        String resultado = formato.format(data);

        // Ajusta a primeira letra do dia da semana para maiúsculo
        resultado = resultado.substring(0, 1).toUpperCase() + resultado.substring(1);

        return resultado;
    }

}
