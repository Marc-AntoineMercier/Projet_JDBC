package marc.connectionDB.requestAction;

import tracky.mam.backend.connectionDB.dbAction.CloseDB;
import tracky.mam.backend.connectionDB.dbAction.ConnectionDB;
import tracky.mam.backend.connectionDB.dbAction.PrepareStatementDB;
import tracky.mam.backend.connectionDB.dbAction.StatementDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public interface UpdateRequest extends ConnectionDB, PrepareStatementDB, StatementDB, CloseDB
{

    default Boolean updateRequest(String query) throws Exception
    {
        Boolean result;
        Connection conn = initConnection();
        Statement stmt = initStatement(conn);
        result = stmt.executeUpdate(query) > 1;
        closeUpdateRequest(conn, stmt);
        return result;
    }

    default Boolean updateRequest(String query, Object[] params) throws Exception
    {
        Boolean result;
        Connection conn = initConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);

        int nbrParam = getNbrParamInQuery(query);

        if(nbrParam != params.length)
        {
            throw new Exception("");
        }

        putParamInPrepareStatement(params, pstmt);

        result = pstmt.executeUpdate() > 1;
        closeUpdateRequest(conn, pstmt);
        return result;
    }
}
