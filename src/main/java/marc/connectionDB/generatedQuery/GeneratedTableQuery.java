package marc.connectionDB.generatedQuery;

import marc.connectionDB.annotation.ManyToOne;
import marc.connectionDB.annotation.OneToOne;
import marc.connectionDB.annotation.Id;
import marc.connectionDB.dbInfo.DataInfo;

import java.lang.reflect.Field;

public interface GeneratedTableQuery extends DataInfo
{
    default String generatedTableQuery() throws Exception
    {
        StringBuilder query = new StringBuilder();

        query.append("Create Table IF NOT EXISTS ")
                .append(this.getClass().getSimpleName())
                .append("(");
        Field[] fields = this.getClass().getDeclaredFields();
        for(int i = 0; i < fields.length; i++)
        {
            query.append(getColumn(fields[i]));
            if(fields[i].getDeclaredAnnotation(Id.class) != null)
            {
                query.append(" PRIMARY KEY")
                        .append(" AUTO_INCREMENT");
            }
            if(fields[i].getDeclaredAnnotation(OneToOne.class) != null || fields[i].getDeclaredAnnotation(ManyToOne.class) != null)
            {
                if(fields[i].getDeclaredAnnotation(OneToOne.class) != null)
                {
                    query.append(" UNIQUE");
                }
                query.append(", FOREIGN KEY (")
                        .append(fields[i].getName())
                        .append(") REFERENCES ")
                        .append(fields[i].getType().getSimpleName())
                        .append("(")
                        .append(getForeightKey(fields[i].getType()))
                        .append(") ");

            }
            if(!(i == fields.length - 1))
            {
                query.append(", ");
            }
        }
        query.append(");");
        return query.toString();
    }

    default String getColumn(Field f) throws Exception
    {
        return f.getName() + " " + convertTypeToSql(f.getType());
    }

    default String convertTypeToSql(Class<?> targetType)
    {
        String type = "";
        if(targetType.isAssignableFrom(String.class))
        {
            type = "VARCHAR(255)";
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
            type = "FLOATB";
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

    default String getForeightKey(Class<?> c)
    {
        StringBuilder columnName = new StringBuilder();
        Field[] fields = c.getDeclaredFields();
        for(int i = 0; i < fields.length; i++)
        {
            if(fields[i].getDeclaredAnnotation(Id.class) != null)
            {
                columnName.append(fields[i].getName());
                break;
            }
        }
        return columnName.toString();
    }
}
