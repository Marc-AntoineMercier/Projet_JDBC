package marc;

import marc.database.generatorSqlSchema.*;
import marc.ioc.IoCContainer;
import marc.ioc.IoCScanner;

public abstract class Application
{
    public static IoCContainer container = new IoCContainer();
    public static String applicationPath = "./data/application.properties";
    public static String scanTable = "marc/test";
    public static String scanIoC = "marc/";

    public static void run() throws Exception {
        IoCScanner.scanAndRegister(scanIoC, container);

        InitializeTableDb initializer = container.resolve(InitializeTableDb.class);
        initializer.initializeTablesAutomatically(scanTable);

    }
}
