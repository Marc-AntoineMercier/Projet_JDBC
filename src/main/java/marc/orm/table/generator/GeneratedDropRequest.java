package marc.orm.table.generator;

import marc.orm.table.annotation.TableName;

public class GeneratedDropRequest
{
    public final String generatedDrop(Class<?> type)
    {
        String tableName = type.isAnnotationPresent(TableName.class) ?
                type.getAnnotation(TableName.class).name():
                type.getSimpleName();
        return "DROP TABLE IF EXISTS " + tableName + ";";
    }
}
