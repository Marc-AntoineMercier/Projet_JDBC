package marc.connectionDB.dbAction;

import tracky.mam.backend.connectionDB.dbInfo.DataInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public interface ConnectionDB extends DataInfo
{
    default Connection initConnection() throws Exception
    {
        Properties prop = getDataInfo();
        return DriverManager.getConnection(prop.getProperty(dbInfo.get("url")), prop.getProperty(dbInfo.get("user")),prop.getProperty(dbInfo.get("password")) );
    }
}
