package marc.actionBd;

import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

@Data
public abstract class RequestBd {

    private String url;
    private String username;
    private String password;
    private String query;
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;


    private static String PROPS_PATH = "./data/application.properties";

    public RequestBd(String query) throws Exception {
        Properties prop = getProps();
        setUrl(prop.getProperty("datasource.url"));
        setUsername(prop.getProperty("datasource.username"));
        setPassword(prop.getProperty("datasource.password"));
        setQuery(query);
    }

    protected Properties getProps() throws Exception{
        Properties props = new Properties();
        props.load(Files.newInputStream(Paths.get(PROPS_PATH)));
        return props;
    };

    protected Connection initConnection() throws Exception{
        return DriverManager.getConnection(url, username, password);
    }

    protected Statement initStatement() throws Exception{
        return getConn().createStatement();
    }

    protected PreparedStatement initPrepareStatement() throws Exception{
        return getConn().prepareStatement(getQuery());
    }

    protected ResultSet executePrepareStatement() throws Exception {
        return getPstmt().executeQuery();
    }

    protected ResultSet executeStatement() throws Exception {
        return getStmt().executeQuery(getQuery());
    }

    protected static void putParamInPrepareStatement(String[] param, PreparedStatement stmt) throws SQLException {
        for(int i = 0; i < param.length; i++){
            stmt.setString(i+1, param[i]);
        }
    }

    protected static int getNbrParam(char[] temp) {
        int nbr = 0;
        for(char c: temp){
            if(c == '?'){
                nbr++;
            }
        }
        return nbr;
    }

    protected void closeAll() throws Exception{
        if(getRs() != null) getRs().close();
        if(getStmt() != null) getStmt().close();
        if(getPstmt() != null) getPstmt().close();
        if(getConn() != null) getConn().close();
    }

}
