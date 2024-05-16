package pe.edu.utp.util;

import java.util.HashMap;
import java.util.Map;

public class Errors {

    private static final Map<String, String> values = new HashMap<>();

    static {
        values.put("EMPTY_FIELDS", "Ambos campos son obligatorios");
        values.put("INCORRECT_PASS", "La contraseña es incorrecta");
        values.put("NOT_USER_EXISTS", "EL DNI no esta registrado");
    }

    public static String get(String key){
        String value = values.get(key);
        if(value == null){
            return "";
        }
        return value;
    }

}