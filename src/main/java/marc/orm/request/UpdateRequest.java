package marc.orm.request;

import java.sql.Connection;
import java.sql.Statement;

public class UpdateRequest extends Request
{
    public void executeStatement(String query) throws Exception {
        Connection conn = initConnection();
        Statement stmt = initStatement(conn);
        stmt.executeUpdate(query);
        close(conn, stmt);
    }
}
