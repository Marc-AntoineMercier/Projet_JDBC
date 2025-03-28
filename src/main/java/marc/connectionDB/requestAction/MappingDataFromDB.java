package marc.connectionDB.requestAction;


import marc.connectionDB.table.TableDB;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface MappingDataFromDB
{

    default <T> List<T> mapSelectRequest(ResultSet resultDB) throws Exception
    {
        List<T> result = new ArrayList<>();

        while(resultDB.next())
        {
            result.add(mapRow(resultDB));
        }

        System.out.println(result);
        return result;
    }

    default  <T> T mapRow(ResultSet resultDB) throws Exception{
        T instance = (T) this.getClass().getDeclaredConstructor().newInstance();
        System.out.println(instance.getClass().getSimpleName());
        for(int i = 1; i <= resultDB.getMetaData().getColumnCount(); i++)
        {
            Object value = resultDB.getObject(i);
            Field f = instance.getClass().getDeclaredField(String.valueOf(resultDB.getMetaData().getColumnName(i)));
            f.setAccessible(true);

            if(value != null && !f.getType().isAssignableFrom(value.getClass()))
            {
                value = convertValue(value, f.getType());
            }

            f.set(instance, value);
        }
        return instance;
    }

    default Object convertValue(Object value, Class<?> type)
    {
        Object result = null;

        if(type.isAssignableFrom(String.class))
        {
            result = value.toString();
        }
        else if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class))
        {
            result = Integer.parseInt(value.toString());
        }
        else if (type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class)) {
            result = Long.parseLong(value.toString());
        }
        else if (type.isAssignableFrom(float.class) || type.isAssignableFrom(Float.class)) {
            result =  Double.parseDouble(value.toString());
        }
        else if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class)) {
            result =  Double.parseDouble(value.toString());
        }
        else if (type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class)) {
            result =  Boolean.parseBoolean(value.toString());
        }

        if(type.isAssignableFrom(TableDB.class))
        {

        }

        return result;
    }
}
