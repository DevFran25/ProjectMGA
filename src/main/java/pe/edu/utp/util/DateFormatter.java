package pe.edu.utp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static LocalDate format(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateadaString = localDate.format(formatter);
        LocalDate fechaFormateada = LocalDate.parse(fechaFormateadaString, formatter);
        return fechaFormateada;
    }

}
