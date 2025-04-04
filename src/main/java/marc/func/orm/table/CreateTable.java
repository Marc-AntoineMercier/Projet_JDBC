package marc.func.orm.table;

import marc.func.orm.request.UpdateRequest;
import marc.func.orm.table.generator.GeneratorTableRequest;

import static marc.app.Application.container;

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
        updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(type));
        updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(type));
    }
}
