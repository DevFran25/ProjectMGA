package pe.edu.utp.servlets;

public class Validator {

    public static void validateNotEmpty(String field, String fieldName) throws IllegalArgumentException {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + fieldName + " no puede estar vacío.");
        }
    }
}
