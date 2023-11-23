package main.utils;

public class NumberHelper {
    
    public static int IntegerParse(String idadeText){
        try {
            return Integer.parseInt(idadeText);
        } catch (NumberFormatException e) {
            return 0; 
        }
    }
    
    public static double DoubleParse(String valorString){
        try {
            return Double.parseDouble(valorString);
        } catch (NumberFormatException e) {
            return 0.0; 
        }
    }
     
}
