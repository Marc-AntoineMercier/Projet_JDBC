package marc.app;

import marc.orm.generatorSqlSchema.InitializeTableDb;
import marc.orm.ioc.IoCContainer;
import marc.orm.ioc.IoCScanner;
import marc.orm.request.UpdateRequest;
import marc.orm.table.generator.GeneratorTableRequest;

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
        UpdateRequest updateRequest = container.resolve(UpdateRequest.class);
        GeneratorTableRequest generatorTableRequest = container.resolve(GeneratorTableRequest.class);

    }
}
