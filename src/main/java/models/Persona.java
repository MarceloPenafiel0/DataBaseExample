package models;

import database.Foreing;

public class Persona implements Foreing {
//pilas pueden usar esta funcion para programar en conjunto
    int id;
    String nombre;
    String apellido;
    int edad;
//    Ciudad ciudad;
    public Persona(String nombre,String apellido, int edad, int id){
        this.id = id;
        this.nombre=nombre;
        this.apellido=apellido;
        this.edad=edad;
//        this.ciudad=new Ciudad("C1","C2");
    }

    public Persona() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}
