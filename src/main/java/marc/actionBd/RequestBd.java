package marc.actionBd;

import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

@Data
public abstract class RequestBd {

    private String url;
    private String username;
    private String password;
    private String query;
    private Connection conn;
    private Statement stmt;
    private ResultSet resultSet;

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
        Properties props = getProps();
        return DriverManager.getConnection(url, username, password);
    }

    protected Statement initStatement() throws Exception{
        return getConn().createStatement();
    }

    protected ResultSet executeResultSet() throws Exception{
        return getStmt().executeQuery(getQuery());
    }

    protected void closeAll() throws Exception{
        if(getResultSet() != null) getResultSet().close();
        if(getStmt() != null) getStmt().close();
        if(getConn() != null) getConn().close();
    }
}
