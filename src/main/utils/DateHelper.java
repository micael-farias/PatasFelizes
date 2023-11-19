package main.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import main.model.Idade;

public class DateHelper {
       
   public static String DataParaString(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss");
        String resultado = formato.format(data);
        resultado = resultado.substring(0, 1).toUpperCase() + resultado.substring(1);
        return resultado;
    }
    
    public static Date LocalDateParaDate(LocalDate localDate) {     
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }
    
    public static Idade CalculaAnosEMesesPorDt(Date data) {
        if (data == null) {
            return null;
        }

        Calendar dataAtual = Calendar.getInstance();
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(data);

        int anos = dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);
        int meses = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);

        if (meses < 0) {
            anos--;
            meses = meses + 12;
        }

        return new Idade(anos,meses);
    }

    public static Date CalculaDtPorMesesEAnos(String ano, String meses) {
        if (meses == null || ano == null) {
            return null;
        }

        try {
            int mesesInt = Integer.parseInt(meses);
            int anoInt = Integer.parseInt(ano);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // Define a data de referência como a data atual

            // Subtrai os anos e meses
            calendar.add(Calendar.YEAR, -anoInt);
            calendar.add(Calendar.MONTH, -mesesInt);

            return calendar.getTime();
        } catch (NumberFormatException e) {
            e.printStackTrace(); // ou trate de acordo com sua lógica de erro
            return null;
        }
    }
}
