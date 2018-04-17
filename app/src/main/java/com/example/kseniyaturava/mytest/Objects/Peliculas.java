package com.example.kseniyaturava.mytest.Objects;

/**
 * Created by kseniyaturava on 30/3/18.
 */

public class Peliculas {

    private String Id_Genero="";
    private String Imagen = "";
    private String Id_Film = "";
    private String Titulo_FIlm = "";
    private String Num_Coments = "";

    public String getNum_Coments() {
        return Num_Coments;
    }

    public void setNum_Coments(String num_Coments) {
        Num_Coments = num_Coments;
    }

    public String getId_Film() {
        return Id_Film;
    }

    public void setId_Film(String id_Film) {
        Id_Film = id_Film;
    }


    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }


    public String getId_Genero() {
        return Id_Genero;
    }

    public void setId_Genero(String id_Genero) {
        Id_Genero = id_Genero;
    }

    public String getTitulo_FIlm() {
        return Titulo_FIlm;
    }

    public void setTitulo_FIlm(String titulo_FIlm) {
        Titulo_FIlm = titulo_FIlm;
    }

}
