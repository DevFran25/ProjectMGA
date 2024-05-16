package pe.edu.utp.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class Log {

    public static void print(String value, HttpServletResponse resp){
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
