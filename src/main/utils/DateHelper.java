package main.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.Date;
import main.model.Idade;

public class DateHelper {
       
   public static String DataParaString(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String resultado = formato.format(data);
        resultado = resultado.substring(0, 1).toUpperCase() + resultado.substring(1);
        return resultado;
    }
   
    public static String CalendarParaString(Calendar calendar) {
        if (calendar == null) {
            return null;
        }

        SimpleDateFormat formato = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String resultado = formato.format(calendar.getTime());
        resultado = resultado.substring(0, 1).toUpperCase() + resultado.substring(1);
        return resultado;
    }
    
    public static String DataParaStringReduced(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(data);
    }
    
    public static Date LocalDateParaDate(LocalDate localDate) {     
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }
      
    public static LocalDate CalendarParaLocalDate(Calendar date) {     
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
 
    public static Idade CalculaAnosEMesesPorDt(Calendar dataNascimento) {
        if (dataNascimento == null) {
            return null;
        }

        Calendar dataAtual = Calendar.getInstance();
        
        int anos = dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);
        int meses = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);

        if (meses < 0) {
            anos--;
            meses = meses + 12;
        }

        return new Idade(anos,meses);
    }
    
    public static Calendar LocalDateParaCalendar(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        // Converte LocalDate para Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());

        return calendar;
    }
    
    public static LocalDate DateParaLocalDate(Date date) {     
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public static Date GetMidnightDate() {
        // Obtendo a instância de Calendar
        Calendar calendar = Calendar.getInstance();

        // Configurando o horário para meia-noite
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date data =  calendar.getTime();
        return data;
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
  
    public static Calendar CalculaDtPorMesesEAnosv2(String ano, String meses) {
        if (meses == null || ano == null) {
            return null;
        }

        try {
            int mesesInt = Integer.parseInt(meses);
            int anoInt = Integer.parseInt(ano);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // Define a data de referência como a data atual

            // Define o dia como 1 para considerar apenas o mês e o ano
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            calendar.add(Calendar.YEAR, -anoInt);
            calendar.add(Calendar.MONTH, -mesesInt);
            return calendar;
        } catch (NumberFormatException e) {
            e.printStackTrace(); // ou trate de acordo com sua lógica de erro
            return null;
        }
    }
    
    public static Calendar DateToCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    
     public static Calendar ConvertMesAnoToCalendar(String ano, String mes) {
        try {
            int mesInt = NumberHelper.IntegerParse(mes);
            int anoInt = NumberHelper.IntegerParse(ano);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            // Configura o mês e o ano
            calendar.add(Calendar.MONTH, -mesInt); // Meses em Calendar começam do 0
            calendar.add(Calendar.YEAR, -anoInt);
            // Zera outros campos para desconsiderar o resto
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar;
        } catch (NumberFormatException e) {
            e.printStackTrace();  // ou trate de acordo com sua lógica de erro
            return null;
        }
    }
}
