package logic;

import database.IManager;
import database.Manager;
import database.Manager2;
import models.Ciudad;
import models.Persona;

public class LogicPersona {
//    public static Persona crearPersona(String nombre, String apellido, int edad, int id){
//        Persona usuario=new Persona(nombre,apellido,edad,id);
//        Manager manager=new Manager();
//        manager.insertar(usuario);
//        return usuario;
//    }

    public static boolean crear(String nombrePersona, String apellidoPersona, int edadPersona, int idPersona,
                                String nombreCiudad, String nombrePais, int idCiudad){
        Persona usuario=new Persona(nombrePersona,apellidoPersona,edadPersona,idPersona);
        Ciudad ciudad = new Ciudad(nombreCiudad,nombrePais,usuario,idCiudad);
        IManager manager=new Manager();

        return manager.insertar(usuario) && manager.insertar(ciudad);
    }

}
