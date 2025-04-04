package marc.orm.table.generator;

import static marc.app.Application.container;

public class GeneratorTableRequest
{
    private final GeneratedDropRequest generatedDropRequest;
    private final GeneratedCreateTableRequest generatedCreateTableRequest;

    public GeneratorTableRequest()
    {
        this.generatedDropRequest = container.resolve(GeneratedDropRequest.class);
        this.generatedCreateTableRequest = container.resolve(GeneratedCreateTableRequest.class);
    }


    public String generatedCreateTableRequest(Class<?> type) throws Exception {
        return generatedCreateTableRequest.generatedCreate(type);
    }

    public String generatedDropTableRequest(Class<?> type)
    {
        return generatedDropRequest.generatedDrop(type);
    }
}
