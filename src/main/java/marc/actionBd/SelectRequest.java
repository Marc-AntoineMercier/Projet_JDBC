package marc.actionBd;

public abstract class SelectRequest extends RequestBd{


    public SelectRequest(String query) throws Exception {
        super(query);
        if(query == null || query.isEmpty()){
            throw new Exception("Le query est obligatoire");
        }else{
            setConn(initConnection());
            setStmt(initStatement());
            setRs(executeStatement());
        }

    }

    public SelectRequest(String query, String[] params) throws Exception {
        super(query);
        if(query == null || query.isEmpty() || params == null || params.length == 0){
            throw new Exception("Le query est obligatoire");
        }else{
            setConn(initConnection());
            setPstmt(initPrepareStatement());

            int nbrParam = getNbrParam(query.toCharArray());
            if(nbrParam != params.length){
                throw new Exception("nombre de parametre different");
            }

            putParamInPrepareStatement(params, getPstmt());

            setRs(executePrepareStatement());
        }

    }
}
