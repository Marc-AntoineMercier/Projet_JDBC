package marc.model;


import lombok.Data;

import java.sql.ResultSet;

@Data
public abstract class AfficheSelectRequest {

    private String resultat;

    public AfficheSelectRequest(SelectRequest selectRequest) throws Exception {
        if(selectRequest.getResultSet() != null && selectRequest.getResultSet().next()) {
            selectRequest.getResultSet().first();
            setResultat("");
            ResultSet rs = selectRequest.getResultSet();
            while(rs.next()) {
                setResultat(getResultat() + "---------\n");
                for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    setResultat(getResultat() + " | " + rs.getObject(i + 1));
                }
                setResultat(getResultat() + " | \n");
            }
        }

    }

    @Override
    public String toString() {
        return resultat;
    }
}
