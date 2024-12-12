package marc.actionBd;


import lombok.Data;

import java.sql.ResultSet;

@Data
public abstract class AfficheSelectRequest {

    private String resultat;

    public AfficheSelectRequest(SelectRequest selectRequest) throws Exception {
        if(selectRequest.getRs() != null && selectRequest.getRs().next()) {
            selectRequest.getRs().first();
            setResultat("");
            ResultSet rs = selectRequest.getRs();
            while(rs.next()) {
                setResultat(getResultat() + "---------\n");
                for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    setResultat(getResultat() + " | " + rs.getObject(i + 1));
                }
                setResultat(getResultat() + " | \n");
            }
        }

        selectRequest.closeAll();

    }

    @Override
    public String toString() {
        return resultat;
    }
}
