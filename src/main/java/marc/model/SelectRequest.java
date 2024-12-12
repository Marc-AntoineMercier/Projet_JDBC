package marc.model;

public abstract class SelectRequest extends RequestBd{

    public SelectRequest(String query) throws Exception {
        super(query);
        setConn(initConnection());
        setStmt(initStatement());
        setResultSet(executeResultSet());

        closeAll();
    }

}
