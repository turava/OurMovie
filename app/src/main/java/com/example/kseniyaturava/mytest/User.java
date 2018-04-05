package com.example.kseniyaturava.mytest;

/**
 * Created by kseniyaturava on 5/4/18.
 */

public class User {
    private String Id_User = "";
    private String Email_User = "";
    private String Edad_User = "";
    private String Ciudad_User = "";
    private String Descripcion = "";

    public String getId_User() {
        return Id_User;
    }

    public void setId_User(String id_User) {
        Id_User = id_User;
    }

    public String getEmail_User() {
        return Email_User;
    }

    public void setEmail_User(String email_User) {
        Email_User = email_User;
    }

    public String getEdad_User() {
        return Edad_User;
    }

    public void setEdad_User(String edad_User) {
        Edad_User = edad_User;
    }

    public String getCiudad_User() {
        return Ciudad_User;
    }

    public void setCiudad_User(String ciudad_User) {
        Ciudad_User = ciudad_User;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


}
