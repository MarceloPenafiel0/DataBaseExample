package models;

import database.Foreing;

public class Ciudad {
    int id;
    String nombreCiudad;
    String paisCiudad;
    Persona persona;

    public Ciudad(String nombreCiudad, String paisCiudad,Persona persona,int id) {
        this.nombreCiudad = nombreCiudad;
        this.paisCiudad = paisCiudad;
//        this.persona =  new Persona("P1","P2",1,1);
        this.persona = persona;
        this.id=id;
    }

    public Ciudad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getPaisCiudad() {
        return paisCiudad;
    }

    public void setPaisCiudad(String paisCiudad) {
        this.paisCiudad = paisCiudad;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
