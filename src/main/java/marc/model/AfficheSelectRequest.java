package marc.model;


import lombok.Data;

import java.sql.ResultSet;

@Data
public class AfficheSelectRequest {

    private String resultat;

    public AfficheSelectRequest(SelectRequest selectRequest) throws Exception {
        setResultat("");
        ResultSet rs = selectRequest.getResultSet();
        while(rs.next()) {
            setResultat(getResultat() + "---------\n");
            for(Integer i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                setResultat(getResultat() + " | " + rs.getObject(i + 1));
            }
            setResultat(getResultat() + " | \n");
        }
    }

    @Override
    public String toString() {
        return resultat;
    }
}