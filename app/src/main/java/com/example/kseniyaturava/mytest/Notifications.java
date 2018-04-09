package com.example.kseniyaturava.mytest;

/**
 * Created by kseniyaturava on 8/4/18.
 */

public class Notifications {
    private String Texto;
    private String Fecha;
    private String Titulo_Film;
    private String Id_Comentario;
    private String Id_Subcomentario;
    private String Id_Foro;

    public String getId_Foro() {
        return Id_Foro;
    }

    public void setId_Foro(String id_Foro) {
        Id_Foro = id_Foro;
    }


    public String getId_Comentario() {
        return Id_Comentario;
    }

    public void setId_Comentario(String id_Comentario) {
        Id_Comentario = id_Comentario;
    }

    public String getId_Subcomentario() {
        return Id_Subcomentario;
    }

    public void setId_Subcomentario(String id_Subcomentario) {
        Id_Subcomentario = id_Subcomentario;
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


}
