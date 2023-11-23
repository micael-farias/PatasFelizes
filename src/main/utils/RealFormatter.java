package main.utils;
import java.text.NumberFormat;
import java.util.Locale;

public class RealFormatter {

    private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static String formatarComoReal(double valor) {
        return FORMATO_MOEDA.format(valor);
    }
    
    public static double unformatarReal(String valor){
        var valorString = valor.replaceAll("[^\\d,]", "").replace(",", ".");       
        return NumberHelper.DoubleParse(valorString);
    }
}
