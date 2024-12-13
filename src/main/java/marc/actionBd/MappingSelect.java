package marc.actionBd;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class MappingSelect<T>
{
    private List<T> listOf;
    private T value;
    protected final Type _type;

    public MappingSelect(SelectRequest selectRequest) throws Exception {
        Type superClass = this.getClass().getGenericSuperclass();
        this._type = ((ParameterizedType)superClass).getActualTypeArguments()[0];

        setListOf(new ArrayList<>());
        ResultSet rs = selectRequest.getRs();
        while(rs.next()){
            if(rs.getMetaData().getColumnCount() > 1){
                getListOf().add(mapRowToClass(rs, (Class<T>) _type));
            }
            else{
                value = (T) convertValue(rs.getObject(1), (Class<?>) _type);
            }
        }
        selectRequest.closeAll();
    }

    private <T> T mapRowToClass(ResultSet resultSet, Class<T> clazz) throws Exception {
        // usagage du principe de reflexion
        T instance = clazz.getDeclaredConstructor().newInstance();

        if(instance.getClass().getDeclaredFields().length != resultSet.getMetaData().getColumnCount()) return null;


        for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            Object value = resultSet.getObject(i+1);
            Field field = instance.getClass().getDeclaredFields()[i];
            field.setAccessible(true);

            if(value != null && !field.getType().isAssignableFrom(value.getClass())) {
                value = convertValue(value, field.getType());
            }

            field.set(instance, value);
        }

        return instance;
    }

    private Object convertValue(Object value, Class<?> targetType) {
        // Ajouter des conversions supplémentaires si nécessaire
        if (targetType.isAssignableFrom(String.class)) {
            return value.toString();
        } else if (targetType.isAssignableFrom(int.class) || targetType.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(value.toString());
        } else if (targetType.isAssignableFrom(long.class) || targetType.isAssignableFrom(Long.class)) {
            return Long.parseLong(value.toString());
        } else if (targetType.isAssignableFrom(double.class) || targetType.isAssignableFrom(Double.class)) {
            return Double.parseDouble(value.toString());
        } else if (targetType.isAssignableFrom(boolean.class) || targetType.isAssignableFrom(Boolean.class)) {
            return Boolean.parseBoolean(value.toString());
        }
        // Retourner la valeur brute si aucune conversion n'est requise
        return value;
    }

    @Override
    public String toString() {
        return getListOf().toString();

    }

}
