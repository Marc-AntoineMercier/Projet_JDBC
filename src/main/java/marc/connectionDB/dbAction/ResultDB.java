package marc.connectionDB.dbAction;

import java.sql.ResultSet;
import java.sql.Statement;

public interface ResultDB
{

    default ResultSet executeStatement(Statement st, String query) throws Exception
    {
        return st.executeQuery(query);
    }


}
