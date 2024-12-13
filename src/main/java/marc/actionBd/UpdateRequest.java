package marc.actionBd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public abstract class UpdateRequest extends RequestBd{

    private Integer countMod = -1;

    public UpdateRequest(String query) throws Exception {
        super(query);
        if(query == null || query.isEmpty()){
            throw new Exception("query est vide");
        }
        else{
            setConn(initConnection());
            setStmt(initStatement());
            countMod = executeUpdateStatement();

            closeAll();
        }
    }

    public UpdateRequest(String query, String[] params) throws Exception{
        super(query);
        if(query == null || query.isEmpty() || params == null || params.length == 0){
            throw new Exception("query est vide ou les parametre sont vides");
        }else{
            setConn(initConnection());
            setPstmt(initPrepareStatement());

            int nbrParam = getNbrParam(getQuery().toCharArray());

            if(nbrParam != params.length){
                throw new Exception("trop de parametre ou manque de parametre");
            }

            putParamInPrepareStatement(params, getPstmt());

            countMod = executeUpdatePrepareStatement();

            closeAll();
        }
    }

    protected int executeUpdateStatement() throws Exception {
        return getStmt().executeUpdate(getQuery());
    }

    protected int executeUpdatePrepareStatement() throws Exception {
        return getPstmt().executeUpdate();
    }

    @Override
    public String toString() {
        return String.valueOf(countMod);
    }
}
