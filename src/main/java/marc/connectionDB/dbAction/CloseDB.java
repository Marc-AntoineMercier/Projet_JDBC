package marc.connectionDB.dbAction;

import java.sql.*;

public interface CloseDB
{
    default void closeAll(Connection conn, Statement stmt, ResultSet rs) throws Exception
    {
        if(rs != null) rs.close();
        if(stmt != null) stmt.close();
        if(conn != null) conn.close();
    }

    default void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) throws Exception
    {
        if(rs != null) rs.close();
        if(pstmt != null) pstmt.close();
        if(conn != null) conn.close();
    }

    default void closeUpdateRequest(Connection conn, Statement stmt) throws Exception {
        if(stmt != null) stmt.close();
        if(conn != null) conn.close();
    }

    default void closeUpdateRequest(Connection conn, PreparedStatement pstmt) throws Exception {
        if(pstmt != null) pstmt.close();
        if(conn != null) conn.close();
    }
}
