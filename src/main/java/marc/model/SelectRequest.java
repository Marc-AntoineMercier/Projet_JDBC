package marc.model;

public class SelectRequest extends RequestBd{

    public SelectRequest(String query) throws Exception {
        super(query);
        setConn(initConnection());
        setStmt(initStatement());
        setResultSet(executeResultSet());

        closeAll();
    }

}
