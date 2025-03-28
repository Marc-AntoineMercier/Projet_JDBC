package marc.connectionDB.table;

import tracky.mam.backend.connectionDB.generatedQuery.GeneratedTableQuery;
import tracky.mam.backend.connectionDB.requestAction.SelectRequest;
import tracky.mam.backend.connectionDB.requestAction.UpdateRequest;

public interface TableDB extends GeneratedTableQuery, UpdateRequest, SelectRequest
{
    default Boolean createTable() throws Exception
    {
        Boolean result = updateRequest(generatedTableQuery());
        if(result)
        {
            System.out.println("La table a bien ete creer");
        }
        return result;
    }

    default Boolean dropTable() throws Exception
    {
        Boolean result = updateRequest("DROP TABLE IF EXISTS " + this.getClass().getSimpleName());
        if(result)
        {
            System.out.println("La table a bien ete creer");
        }
        return result;
    }
}
