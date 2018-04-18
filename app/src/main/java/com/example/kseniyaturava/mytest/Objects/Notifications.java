package com.example.kseniyaturava.mytest.Objects;

/**
 * Created by kseniyaturava on 8/4/18.
 */

public class Notifications {
    private String Texto;
    private String Fecha;
    private String Titulo_Film;
    private String User;
    private String Id_Notificacion;
    private String Leido;

    public String getLeido() {return Leido;}
    public void setLeido(String leido) {Leido = leido;}

    public String getId_Notificacion() {
        return Id_Notificacion;
    }
    public void setId_Notificacion(String id_Notificacion) {
        Id_Notificacion = id_Notificacion;
    }

    public String getTitulo_Film() {
        return Titulo_Film;
    }
    public void setTitulo_Film(String titulo_Film) {
        Titulo_Film = titulo_Film;
    }

    public String getTexto() {
        return Texto;
    }
    public void setTexto(String texto) {
        Texto = texto;
    }

    public String getFecha() {
        return Fecha;
    }
    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getUser() {
        return User;
    }
    public void setUser(String user) {
        User = user;
    }
}
