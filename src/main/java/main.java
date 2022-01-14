import database.ConexionMySql;
import database.Manager;
import models.Ciudad;
import models.Persona;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class main {
    public static void main(String[] args) {
//        ConexionMySql conector = new ConexionMySql();
//        conector.conectar();

//        JFrame frame = new JFrame("VentanaIngreso");
//        frame.setContentPane(new VentanaIngreso().panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

//        Persona o = new Persona("P1","P2",1,1);
        Persona o = new Persona();
        o.setId(1);
        o.setEdad(1);
        Manager m = new Manager();

        o = (Persona) m.leer(o);
        Ciudad c = new Ciudad();
        c.setId(1);
        c = (Ciudad) m.leer(c);
        System.out.println(c.getNombreCiudad());


//        m.insertar(o);
//
//        Object o0 = new Ciudad("Cuenca","Ecuador");
//
//        m.insertar(o0);

//        o.setApellido("Ape");
//
//        m.modificar(o);
//
//        m.borrar(o0);

//        String test = "id";
//        String test2 = "idPersona";
//
//        for (String s : test2.split("id")){
//            System.out.println(s);
//        }

    }

}
