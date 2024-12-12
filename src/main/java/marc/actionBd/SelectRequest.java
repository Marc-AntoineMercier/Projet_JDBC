package marc.actionBd;

public abstract class SelectRequest extends RequestBd{

    public SelectRequest(String query) throws Exception {
        super(query);
        if(query != null && !query.isEmpty()){
            setConn(initConnection());
            setStmt(initStatement());
            setResultSet(executeResultSet());

            closeAll();
        }
    }

}
