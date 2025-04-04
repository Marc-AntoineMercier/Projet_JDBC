package marc.app;

import marc.func.orm.generatorSqlSchema.InitializeTableDb;
import marc.func.ioc.IoCContainer;
import marc.func.ioc.IoCScanner;
import marc.func.orm.request.UpdateRequest;
import marc.func.orm.table.generator.GeneratorTableRequest;

public abstract class Application
{
    public static IoCContainer container = new IoCContainer();
    public static String applicationPath = "src/main/java/marc/resource/application.properties";
    public static String scanTable = "marc/app";
    public static String scanIoC = "marc/";

    public static void run() throws Exception {
        IoCScanner.scanAndRegister(scanIoC, container);

        container
                .resolve(InitializeTableDb.class)
                .initializeTablesAutomatically(scanTable);

    }
}
