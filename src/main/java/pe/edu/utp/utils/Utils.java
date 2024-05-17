package pe.edu.utp.utils;

import java.util.UUID;

public class Utils {

    public static String crearToken(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
