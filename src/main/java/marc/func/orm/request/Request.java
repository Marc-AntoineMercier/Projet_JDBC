package marc.func.orm.request;

import lombok.Data;
import marc.func.orm.database.DataBaseConfig;

import java.sql.*;

import static marc.app.Application.container;

@Data
public class Request
{
    protected final DataBaseConfig dataBaseConfig;
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;


    public Request()
    {
        this.dataBaseConfig = container.resolve(DataBaseConfig.class);
    }

    protected Connection initConnection() throws Exception
    {
        return DriverManager.getConnection(dataBaseConfig.getUrl(), dataBaseConfig.getUsername(), dataBaseConfig.getPassword());
    }

    protected Statement initStatement(Connection conn) throws Exception
    {
        return conn.createStatement();
    }

    protected PreparedStatement initPrepareStatement(Connection conn, String query) throws Exception
    {
        return conn.prepareStatement(query);
    }

    protected Integer getNbrParamInQuery(String query) throws Exception
    {
        Integer nbr = 0;
        for(Character c : query.toCharArray())
        {
            if(c == '?')
            {
                nbr++;
            }
        }
        return nbr;
    }

    protected void putParamInPrepareStatement(Object[] params, PreparedStatement pstmt) throws Exception
    {
        for(int i = 0; i < params.length; i++)
        {
            pstmt.setObject(i, params[i]);
        }
    }

    protected void close(Connection conn, Statement stmt) throws Exception {
        if(!conn.isClosed()) conn.close();
        if(!stmt.isClosed()) stmt.close();
    }

    protected void close(Connection conn, PreparedStatement pstmt) throws Exception {
        if(!conn.isClosed()) conn.close();
        if(!pstmt.isClosed()) pstmt.close();
    }
}
