package marc.app;

import marc.app.model.Test1;
import marc.app.model.Test2;
import marc.app.model.Test3;
import marc.app.model.Test4;
import marc.func.generatorSqlSchema.InitializeTableDb;
import marc.func.ioc.IoCContainer;
import marc.func.ioc.IoCScanner;
import marc.func.request.UpdateRequest;
import marc.func.table.generator.GeneratorTableRequest;

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

//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test4.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test3.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test2.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test1.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(Test1.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(Test2.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(Test3.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(Test4.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test4.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test3.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test2.class));
//        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(Test1.class));

    }
}
