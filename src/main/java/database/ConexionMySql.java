package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionMySql {
    private static ConexionMySql INSTANCE;

    private final String USER = "root";
    private final String PASSWORD = "0000";
    private final String NAME_DATABASE = "testDb";
    private final String jdbc = "jdbc:mysql://localhost:3306/"+NAME_DATABASE;
    private final String JDBC = "jdbc:mariadb://localhost/" + NAME_DATABASE + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static Connection conexion;

    public ConexionMySql(){}

    public synchronized Connection conectar(){
        conexion = null;
        try {
            conexion=DriverManager.getConnection(JDBC,USER,PASSWORD);
            //Class for name ya no es necesario a partir de jdbc 4
            conexion.setAutoCommit(false);//Manjear los commits parcialmente a conveniencia.
            System.out.println("Conectado a Mysql exitosamente a la base de datos: "+NAME_DATABASE);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMySql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }

    public static synchronized ConexionMySql getInstance() {
        if(INSTANCE==null){
            INSTANCE=new ConexionMySql();
        }
        return INSTANCE;
    }
    public static synchronized Connection getConnection() {
        return conexion;
    }


    public void desconectar() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMySql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public String getNameDataBase() {
        return NAME_DATABASE;
    }

}