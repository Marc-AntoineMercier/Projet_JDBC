package marc.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServiceBd {
    /*
    private static final String PROPS_PATH = "/data/application.properties";


    private static Connection initConnBd() throws Exception {
        Properties prop = getProps();
        return DriverManager.getConnection(
                prop.getProperty("datasource.url"),
                prop.getProperty("datasource.username"),
                prop.getProperty("datasource.password"));
    }

    private static void closeAll(Connection con, Statement stmt, ResultSet rst) throws Exception {
        if(rst != null) rst.close();
        if(stmt != null) stmt.close();
        if(con != null) con.close();
    }

    private static void closeAll(Connection con, Statement stmt) throws Exception {
        if(stmt != null) stmt.close();
        if(con != null) con.close();
    }

    public static Properties getProps () throws Exception{
        Properties props = new Properties();
        props.load(new FileInputStream(PROPS_PATH));
        return props;
    }

    private static ResultSet executeStmt(String query, Statement stmt) throws Exception {
        return stmt.executeQuery(query);
    }

    private static Statement initStmt(Connection conn) throws Exception {
        return conn.createStatement();
    }

    public static ResultSet selectQuery(String query) throws Exception {
        if(query == null && query.equals("")) return null;

        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;

        conn = initConnBd();
        stmt = initStmt(conn);
        rs = executeStmt(query, stmt);

        closeAll(conn, stmt, rs);
        return rs;
    }

    public static ResultSet selectQuery(String query, String[] param) throws Exception {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        conn = initConnBd();
        stmt = conn.prepareStatement(query);

        int nbrParam = 0;

        nbrParam = getNbrParam(query.toCharArray());

        if (nbrParam != param.length) {
            return null;
        }

        putParamInPrepareStatement(param, stmt);

        rs = stmt.executeQuery();


        closeAll(conn, stmt, rs);
        return rs;
    }

    private static void putParamInPrepareStatement(String[] param, PreparedStatement stmt) throws SQLException {
        for(int i = 0; i < param.length; i++){
            stmt.setString(i+1, param[i]);
        }
    }

    private static int getNbrParam(char[] temp) {
        int nbr = 0;
        for(char c: temp){
            if(c == '?'){
                nbr++;
            }
        }
        return nbr;
    }

    public static boolean updateQuery(String query) throws Exception {
        boolean flag = false;
        Connection conn = null;
        Statement stmt = null;
        int countInsert = -1;

        conn = initConnBd();
        stmt = conn.createStatement();
        countInsert = stmt.executeUpdate(query);

        flag = countInsert >= 0;

        closeAll(conn, stmt);
        return flag;
    }

    public static boolean updateQuery(String query, String[] param) throws Exception {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        int countInsert = -1;

        conn = initConnBd();
        stmt = conn.prepareStatement(query);


        char[] temp = query.toCharArray();
        int nbrParam = 0;

        nbrParam = getNbrParam(temp);

        if (nbrParam != param.length) {
            return flag;
        }

        putParamInPrepareStatement(param, stmt);

        countInsert = stmt.executeUpdate();

        flag = countInsert >= 0;

        closeAll(conn, stmt);
        return flag;
    }


    // pas tester encore
    public static boolean putFile(String query, String pathFile, String id) throws Exception{
        boolean flag = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        int countInsert = -1;
        FileInputStream input = null;

        conn = initConnBd();
        stmt = conn.prepareStatement(query);

        File file = new File(pathFile);
        input = new FileInputStream(file);
        stmt.setBinaryStream(1, input);
        stmt.setString(2, id);
        stmt.executeUpdate();

        closeAll(conn, stmt);
        return flag;
    }

    // pas tester encore
    public static ResultSet getFile(String query, String insertPathFile, String id, String colName) throws Exception{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement stmt = null;


        conn = initConnBd();
        stmt = conn.prepareStatement(query);
        stmt.setString(1, id);

        rs = stmt.executeQuery();

        closeAll(conn, stmt, rs);
        return rs;
    }

    public static boolean createFile(ResultSet rs, String colName, String insertPathFile) throws Exception {
        boolean flag = false;
        FileOutputStream fos = null;
        InputStream input = null;
        File f = new File(insertPathFile);
        fos = new FileOutputStream(f);

        while (rs.next()) {
            input = rs.getBinaryStream(colName);
            byte[] buffer = new byte[1024];
            while(input.read(buffer) > 0){
                fos.write(buffer);
            }
        }

        flag = new File(insertPathFile).exists() && new File(insertPathFile).length() > 0;
        return flag;
    }

    private static <T> T mapRowToClass(ResultSet resultSet, Class<T> clazz) throws Exception {
        // usagage du principe de reflexion
        T instance = clazz.getDeclaredConstructor().newInstance();

        if(instance.getClass().getDeclaredFields().length != resultSet.getMetaData().getColumnCount()) return null;


        for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            Object value = resultSet.getObject(i+1);
            Field field = instance.getClass().getDeclaredFields()[i];
            field.setAccessible(true);

            if(value != null && !field.getType().isAssignableFrom(value.getClass())) {
                value = convertValue(value, field.getType());
            }

            field.set(instance, value);
        }

        return instance;
    }

    private static Object convertValue(Object value, Class<?> targetType) {
        // Ajouter des conversions supplémentaires si nécessaire
        if (targetType.isAssignableFrom(String.class)) {
            return value.toString();
        } else if (targetType.isAssignableFrom(int.class) || targetType.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(value.toString());
        } else if (targetType.isAssignableFrom(long.class) || targetType.isAssignableFrom(Long.class)) {
            return Long.parseLong(value.toString());
        } else if (targetType.isAssignableFrom(double.class) || targetType.isAssignableFrom(Double.class)) {
            return Double.parseDouble(value.toString());
        } else if (targetType.isAssignableFrom(boolean.class) || targetType.isAssignableFrom(Boolean.class)) {
            return Boolean.parseBoolean(value.toString());
        }
        // Retourner la valeur brute si aucune conversion n'est requise
        return value;
    }

    public static <T> List<T> mappingSelectAll(ResultSet rs, Class<T> clazz) throws Exception {
        List<T> oList = new ArrayList<>();
        while (rs.next()) {
            oList.add(mapRowToClass(rs, clazz));
        }
        return oList;
    }

    public static boolean afficheRs(ResultSet rs) throws Exception {
        boolean flag = false;
        if(rs == null) return flag;

        // Ligne
        System.out.println(rs.getMetaData().getTableName(1));
        while (rs.next()) {
            System.out.println("-------------------------------------------------------");
            // Colonne
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                if(rs.getMetaData().getColumnCount() > 1){
                    System.out.print(" | ");
                }
                System.out.print(rs.getObject(i));
            }
        }

        flag = true;
        return flag;
    }
    */
}