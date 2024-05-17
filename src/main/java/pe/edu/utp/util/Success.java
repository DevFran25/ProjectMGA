package pe.edu.utp.util;

import java.util.HashMap;
import java.util.Map;

public class Success {

    private static final Map<String, String> values = new HashMap<>();

    static {
        values.put("UPDATED_PASSWORD", "Contraseña actualizada correctamente");
    }

    public static String get(String key){
        String value = values.get(key);
        if(value == null){
            return "";
        }
        return value;
    }

}
