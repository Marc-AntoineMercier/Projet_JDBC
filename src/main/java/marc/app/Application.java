package marc.app;

import marc.framework.generatorSqlSchema.InitializeTableDb;
import marc.framework.ioc.IoCContainer;
import marc.framework.ioc.IoCScanner;

public abstract class Application
{
    public static IoCContainer container = new IoCContainer();
    public static String applicationPath = "src/main/java/marc/resource/application.properties";
    public static String scanTable = "marc/app";
    public static String scanIoC = "marc/";

    public static void run() throws Exception {
        IoCScanner.scanAndRegister(scanIoC, container);

        InitializeTableDb initializer = container.resolve(InitializeTableDb.class);
        initializer.initializeTablesAutomatically(scanTable);

    }
}
