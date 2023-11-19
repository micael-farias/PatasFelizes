package main.utils;

public class IntegerHelper {
    
    public static int IntegerParse(String idadeText){
        try {
            return Integer.parseInt(idadeText);
        } catch (NumberFormatException e) {
            return 0; 
        }
    }
}
