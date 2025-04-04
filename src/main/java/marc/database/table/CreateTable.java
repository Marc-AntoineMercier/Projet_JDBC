package marc.database.table;

import marc.database.request.UpdateRequest;
import marc.database.table.generator.GeneratorTableRequest;

import static marc.Application.container;

public class CreateTable
{
    private final UpdateRequest updateRequest;
    private final GeneratorTableRequest generatorTableRequest;

    public CreateTable()
    {
        this.updateRequest = container.resolve(UpdateRequest.class);
        this.generatorTableRequest = container.resolve(GeneratorTableRequest.class);
    }

    public void createTable(Class<?> type) throws Exception {
        System.out.println(generatorTableRequest.generatedDropTableRequest(type));
        System.out.println(generatorTableRequest.generatedCreateTableRequest(type));
        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(type));
        updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(type));
    }
}
