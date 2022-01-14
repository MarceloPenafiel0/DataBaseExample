package database;

import java.lang.reflect.*;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Manager implements IManager{

    private Connection conexionMySql;

    public Manager() {

        conexionMySql = new ConexionMySql().conectar();
    }

    @Override
    public boolean insertar(Object objeto) {

        String nombreTabla = objeto.getClass().getSimpleName();

        HashMap<String,String> atributes = getAtributes(objeto);


        String aux1 = "";
        String aux2 = "VALUES ( ";
        for (Map.Entry<String,String> entry : atributes.entrySet()){
            aux1+=entry.getKey() + ", ";
            aux2+=entry.getValue()+", ";
        }
        aux1 = aux1.substring(0,aux1.length()-2);
        aux2 = aux2.substring(0,aux2.length()-2);

        String query = "INSERT INTO "+ nombreTabla +" (" + aux1 +") "+ aux2 + ");";  // Ej: INSERT INTO PERSONA (id,nombre,apellido) VALUES (1,"N1","P1");


        System.out.println(query);
        try{
            Statement stmt = conexionMySql.createStatement();
            System.out.println(stmt.execute(query));
            stmt.close();
            conexionMySql.commit();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean modificar(Object objeto) {
        String nombreTabla = objeto.getClass().getSimpleName();

        HashMap<String,String> atributes = getAtributes(objeto);

        String aux1 = " SET ";
        String aux2 = "";
        for (Map.Entry<String,String> entry : atributes.entrySet()){
            if (entry.getKey().equals("id")){
                aux2+= " WHERE id="+entry.getValue()+";";
            }else {
                aux1 += entry.getKey()+"="+entry.getValue() + ", ";
            }
        }

        aux1 = aux1.substring(0,aux1.length()-2);

        String query = "UPDATE "+nombreTabla + aux1 + aux2;

        System.out.println(query);
        try{
            Statement stmt = conexionMySql.createStatement();
            System.out.println(stmt.execute(query));
            stmt.close();
            conexionMySql.commit();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean borrar(Object objeto) {
        String nombreTabla = objeto.getClass().getSimpleName();

        HashMap<String,String> atributes = getAtributes(objeto);

        String aux2 = " WHERE ";
        for (Map.Entry<String,String> entry : atributes.entrySet()){
            aux2+= entry.getKey()+"="+entry.getValue()+" and ";
        }
        aux2 = aux2.substring(0,aux2.length()-4);
        aux2+=";";
        String query = "DELETE FROM "+nombreTabla + aux2;

        System.out.println(query);
        try{
            Statement stmt = conexionMySql.createStatement();
            System.out.println(stmt.execute(query));
            stmt.close();
            conexionMySql.commit();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Object leer(Object objeto) {

//        String query = "SELECT * FROM Persona WHERE id=1;";

        String nombreTabla = objeto.getClass().getSimpleName();

        HashMap<String,String> atributes = getAtributesNoNull(objeto);


        String aux2 = " WHERE ";
        for (Map.Entry<String,String> entry : atributes.entrySet()){
            aux2+= entry.getKey()+"="+entry.getValue()+" and ";
        }
        aux2 = aux2.substring(0,aux2.length()-4);
        aux2+=";";

        String query = "SELECT * FROM "+nombreTabla +aux2;

        try{
            System.out.println(query);
            Statement stmt = conexionMySql.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            objeto = reconstruct(rs,objeto);
            stmt.close();
            return objeto;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Object leerPorId(Object object){
        try {
            Field value = object.getClass().getDeclaredField("id");
            value.setAccessible(true);
            String tableName = object.getClass().getSimpleName();
            String query = "SELECT * FROM "+tableName + " WHERE id="+value.get(object)+";";
            System.out.println(query);
            Statement stmt = conexionMySql.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            object = reconstruct(rs,object);
            stmt.close();
            return object;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public Object reconstruct(ResultSet rs,Object objeto) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchFieldException {
        ResultSetMetaData meta = rs.getMetaData();
        rs.next();
        for (int i =1 ; i<=meta.getColumnCount();i++){
            System.out.println(rs.getObject(i));
            if(meta.getColumnName(i).matches("(id)([A-Z])\\w+")){
                String[] aux = meta.getColumnName(i).split("id");
                Class<?> clazz = objeto.getClass().getDeclaredField(defund(meta.getColumnName(i).split("id")[1])).getType();

//                Class<?> clazz = Class.forName(className);
                Constructor<?> ctor = clazz.getConstructor();
                Object auxObject = ctor.newInstance();
                Method auxMethod = clazz.getMethod("setId",int.class);
                auxMethod.invoke(auxObject,rs.getObject(i));

                auxObject = leerPorId(auxObject);
                String methodName = "set" + aux[1];
                Method method = objeto.getClass().getMethod(methodName,auxObject.getClass());
                method.invoke(objeto, auxObject);

            }else {
                String methodName = "set" + capitalize(meta.getColumnName(i));
                Method method = objeto.getClass().getMethod(methodName,rs.getObject(i).getClass());
                method.invoke(objeto, rs.getObject(i));
            }
        }
        return objeto;
    }

    public HashMap<String,String> getAtributes(Object object){
        HashMap<String,String> atributes = new HashMap<>();
        ArrayList<String> interfaces = new ArrayList<>();

        Field[] atributos = object.getClass().getDeclaredFields();

        try {
            for (Field atributo : atributos) {
//
                Class<?>[] classes = atributo.getType().getInterfaces();

                for(Class<?> c : classes){
                    interfaces.add(c.getSimpleName());
                }
                atributo.setAccessible(true);
                Object valorAtributo;
                if (interfaces.contains("Foreing")){
                    Object auxObject = atributo.get(object);
                    Field auxField = auxObject.getClass().getDeclaredField("id");
                    auxField.setAccessible(true);
                    valorAtributo = auxField.get(auxObject);
                    atributes.put("id"+atributo.getName(),valorAtributo.toString()); // (idPersona,1)
                    System.out.println("id"+atributo.getName()+":"+valorAtributo.toString());
                }else {

                    valorAtributo = atributo.get(object);
                    if (atributo.getType().getSimpleName().equals("String")){
                        valorAtributo = "'" +atributo.get(object)+"'";
                    }

                    System.out.println(atributo.getName() + ": " + valorAtributo);
                    atributes.put(atributo.getName(), valorAtributo.toString());
                }
                interfaces.clear();
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return atributes;
    }

    public HashMap<String,String> getAtributesNoNull(Object object) {
        HashMap<String,String> atributes = new HashMap<>();
        ArrayList<String> interfaces = new ArrayList<>();

        Field[] atributos = object.getClass().getDeclaredFields();

        try {
            for (Field atributo : atributos) {
//
                Class<?>[] classes = atributo.getType().getInterfaces();

                for(Class<?> c : classes){
                    interfaces.add(c.getSimpleName());
                }
                atributo.setAccessible(true);
                Object valorAtributo;
                if (interfaces.contains("Foreing")){
                    Object auxObject = atributo.get(object);
                    if (auxObject != null) {
                        Field auxField = auxObject.getClass().getDeclaredField("id");
                        auxField.setAccessible(true);
                        valorAtributo = auxField.get(auxObject);
                        atributes.put("id" + atributo.getName(), valorAtributo.toString());
                        System.out.println("id" + atributo.getName() + ":" + valorAtributo.toString());
                    }
                }else {
                    valorAtributo = atributo.get(object);
                    if (!Objects.isNull(valorAtributo)) {
                        if (atributo.getType().getSimpleName().equals("String")) {
                            valorAtributo = "'" + atributo.get(object) + "'";
                        }

                        System.out.println(atributo.getName() + ": " + valorAtributo);

                        atributes.put(atributo.getName(), valorAtributo.toString());
                    }
                }
                interfaces.clear();
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return atributes;
    }

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String defund(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
