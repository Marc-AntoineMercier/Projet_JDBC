package marc.connectionDB.requestAction;

import marc.connectionDB.dbAction.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface SelectRequest extends ConnectionDB, PrepareStatementDB, StatementDB, CloseDB, MappingDataFromDB, ResultDB
{

    default <T> List<T> selectRequest(String query) throws Exception {
        Connection conn = initConnection();
        Statement stmt = initStatement(conn);
        ResultSet reS = executeStatement(stmt, query);

        return mapSelectRequest(reS);
    }
}
