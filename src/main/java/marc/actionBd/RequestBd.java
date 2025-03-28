package marc.actionBd;

import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;


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

    protected final Properties getProps() throws Exception{
        Properties props = new Properties();
        props.load(Files.newInputStream(Paths.get(PROPS_PATH)));
        return props;
    };

    protected final Connection initConnection() throws Exception{
        return DriverManager.getConnection(url, username, password);
    }

    protected final Statement initStatement() throws Exception{
        return getConn().createStatement();
    }

    protected final PreparedStatement initPrepareStatement() throws Exception{
        return getConn().prepareStatement(getQuery());
    }

    protected final ResultSet executePrepareStatement() throws Exception {
        return getPstmt().executeQuery();
    }

    protected final ResultSet executeStatement() throws Exception {
        return getStmt().executeQuery(getQuery());
    }

    protected final static void putParamInPrepareStatement(String[] param, PreparedStatement stmt) throws SQLException {
        for(int i = 0; i < param.length; i++){
            stmt.setString(i+1, param[i]);
        }
    }

    protected final static int getNbrParam(char[] temp) {
        int nbr = 0;
        for(char c: temp){
            if(c == '?'){
                nbr++;
            }
        }
        return nbr;
    }

    protected final void closeAll() throws Exception{
        if(getRs() != null) getRs().close();
        if(getStmt() != null) getStmt().close();
        if(getPstmt() != null) getPstmt().close();
        if(getConn() != null) getConn().close();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getPropsPath() {
        return PROPS_PATH;
    }

    public static void setPropsPath(String propsPath) {
        PROPS_PATH = propsPath;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
