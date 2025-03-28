package marc.connectionDB.dbAction;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface PrepareStatementDB
{

    default PreparedStatement initPrepareStatement(Connection conn, String query) throws Exception
    {
        return conn.prepareStatement(query);
    }

    default int getNbrParamInQuery(String query) throws Exception
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

    default void putParamInPrepareStatement(Object[] params, PreparedStatement pstmt) throws Exception
    {
        for(int i = 0; i < params.length; i++)
        {
            pstmt.setObject(i, params[i]);
        }
    }
}
