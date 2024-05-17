package pe.edu.utp.utils;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

public class Email {

    private String email;
    private String nombre;
    private String token;

    public Email(String email, String nombre, String token) {
        this.email = email;
        this.nombre = nombre;
        this.token = token;
    }

    public void enviarInstrucciones(){
        Resend resend = new Resend("re_XqhJHvGB_JdN9t41DR1RkR5k59THpvAGv");
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Team <onboarding@resend.dev>")
                .to("delivered@resend.dev")
                .subject("Instrucciones para cambiar la contraseña")
                .html("<p><strong>Hola " + this.nombre + "</strong> Has solicitado reestablecer tu password, sigue el siguiente" +
                        "enlace para hacerlo</p><p>Presiona aquí: " +
                        "<a href='http://localhost:8085/reestablecer?token="+ this.token +"'>Reestablecer contraseña</a></p>" +
                        "<p>Si tu no solicitaste este cambio, puedes ignorar el mensaje</p>")
                .build();
        try {
            CreateEmailResponse data = resend.emails().send(params);
        } catch (ResendException e) {
            throw new RuntimeException(e);
        }

    }

}
