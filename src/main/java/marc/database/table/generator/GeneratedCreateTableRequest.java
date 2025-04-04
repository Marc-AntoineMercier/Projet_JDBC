package marc.database.table.generator;

import marc.database.table.annotation.Column;
import marc.database.table.annotation.Id;
import marc.database.table.annotation.TableName;
import marc.database.table.annotation.relation.ManyToOne;
import marc.database.table.annotation.relation.OneToOne;

import java.lang.reflect.Field;
import java.util.Objects;

public class GeneratedCreateTableRequest
{

    public final String generatedCreate(Class<?> type) throws Exception {
        StringBuilder query = new StringBuilder("CREATE TABLE ")
                .append(type.isAnnotationPresent(TableName.class) ?
                        type.getAnnotation(TableName.class).name() : type.getSimpleName())
                .append(" (");
        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            query.append(getColumn(field));

            if(field.isAnnotationPresent(Id.class))
            {
                query.append(" PRIMARY KEY")
                        .append(" AUTO_INCREMENT");
            }
            if(field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class))
            {
                if (field.isAnnotationPresent(OneToOne.class))
                {
                    query.append(" UNIQUE");
                }
                query.append(", FOREIGN KEY (")
                    .append(field.getName())
                    .append(") REFERENCES ")
                    .append(field.getType().getSimpleName())
                    .append("(")
                    .append(getForeightKey(field))
                    .append(") ");

            }

            if (i < fields.length - 1) {
                query.append(", ");
            }
        }

        query.append(");");
        return query.toString();
    }

    String getColumn(Field field) throws Exception
    {
        String fieldName = field.isAnnotationPresent(Column.class) ?
                field.getAnnotation(Column.class).name() :
                field.getName();
        String fieldOption = getFieldOptions(field);
        return fieldName + " " + convertTypeToSql(field) + fieldOption;
    }

    String convertTypeToSql(Field field)
    {
        Class<?> targetType = field.getType();
        String type = "";
        if(targetType.isAssignableFrom(String.class))
        {
            type = "VARCHAR(" +
                    (field.isAnnotationPresent(Column.class) ?
                            Math.abs(Math.min(field.getAnnotation(Column.class).value(), 255)) :
                            "255") +
                    ")";
        }
        else if (targetType.isAssignableFrom(int.class) || targetType.isAssignableFrom(Integer.class))
        {
            type = "INT";
        }
        else if (targetType.isAssignableFrom(long.class) || targetType.isAssignableFrom(Long.class))
        {
            type = "INT";
        }
        else if (targetType.isAssignableFrom(double.class) || targetType.isAssignableFrom(Double.class))
        {
            type = "DOUBLE";
        } else if (targetType.isAssignableFrom(float.class) || targetType.isAssignableFrom(Float.class))
        {
            type = "FLOAT";
        }
        else if (targetType.isAssignableFrom(boolean.class) || targetType.isAssignableFrom(Boolean.class)) {
            type = "BIT";
        }
        else
        {
            type = "INT";
        }

        return type;
    }

    public String getFieldOptions(Field field)
    {
        String options = "";
        if(!field.isAnnotationPresent(Column.class))
        {
            return "";
        }

        options += !Objects.equals(field.getAnnotation(Column.class).nullable(), "") ?
                " NOT NULL " :
                "";

        options += !Objects.equals(field.getAnnotation(Column.class).defaultValue(), "") ?
                " DEFAULT '" + field.getAnnotation(Column.class).defaultValue() + "'":
                "";

        return options;
    }

    public String getForeightKey(Field field)
    {
        StringBuilder columnName = new StringBuilder();
        Field[] fields = field.getType().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                if (f.isAnnotationPresent(Column.class)) {
                    columnName.append(f.getAnnotation(Column.class).name());
                } else {
                    columnName.append(f.getName());
                }
                break;
            }
        }
        return columnName.length() > 0 ? columnName.toString() : null;
    }
}
