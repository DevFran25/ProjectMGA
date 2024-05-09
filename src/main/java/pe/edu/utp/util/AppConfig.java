package pe.edu.utp.util;

import java.util.ResourceBundle;

public class AppConfig {

    static ResourceBundle rb = ResourceBundle.getBundle("config");
    public static String getConnectionStringCFN(){
        return rb.getString("conenctionString");
    }
    public static String getErrorLogFile(){
        return rb.getString("errorLog");
    }

    //Separador
    public static String separator(){
        return System.getProperty("file.separator");
    }

    //Imagenes
    public static String getImgDir(){ return rb.getString("upload_dir"); }

    public static String getErrorTemplate() { return getTemplateDir() + separator() + "error.html";}

    public static String getRegistroProyecto() { return getWebDir() + separator() + "index.html";}

    //Web Y Template
    public static String getTemplateDir(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return rb.getString("template_dir");
        }else{
            return rb.getString("template_dir_unix");
        }
    }

    public static String getWebDir(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return rb.getString("document_root");
        }else{
            return rb.getString("doc_root_unix");
        }
    }
}
