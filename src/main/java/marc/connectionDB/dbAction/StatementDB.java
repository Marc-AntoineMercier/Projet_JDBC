package marc.connectionDB.dbAction;

import java.sql.Connection;
import java.sql.Statement;

public interface StatementDB
{
    default Statement initStatement(Connection conn) throws Exception
    {
        return conn.createStatement();
    }
}
