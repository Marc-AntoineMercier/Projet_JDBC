package marc.connectionDB.dbInfo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public interface DataInfo
{

    String PROPS_PATH = "src/main/resources/application.properties";
    Map<String, String> dbInfo = Map.of( "url", "spring.datasource.url", "user", "spring.datasource.username", "password", "spring.datasource.password");

    default Properties getDataInfo() throws Exception
    {
        Properties prop = new Properties();
        prop.load(Files.newInputStream(Paths.get(PROPS_PATH)));
        return prop;
    }
}
