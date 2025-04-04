package marc.framework.database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static marc.app.Application.applicationPath;


public class DataBaseConfig
{
    private String url;
    private String username;
    private String password;

    public DataBaseConfig() throws Exception
    {
        Properties prop = getProp();
        this.url = prop.getProperty("datasource.url");
        this.username = prop.getProperty("datasource.username");
        this.password = prop.getProperty("datasource.password");
    }

    private final Properties getProp() throws Exception
    {
        Properties prop = new Properties();
        prop.load(Files.newInputStream(Paths.get(applicationPath)));
        return prop;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
