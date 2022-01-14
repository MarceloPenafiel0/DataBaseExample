package database;

import java.util.HashMap;

public interface IManager {
    boolean insertar(Object objeto);
    boolean modificar(Object objeto);
    boolean borrar(Object objeto);
    Object leer(Object objeto);
}
