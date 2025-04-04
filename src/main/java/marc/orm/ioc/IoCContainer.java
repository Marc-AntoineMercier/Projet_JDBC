package marc.orm.ioc;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class IoCContainer
{

    private HashMap<Class<?>, Object> container = new HashMap<>();


    public void register(Class<?> type, Object instance)
    {
        container.put(type, instance);
    }
    public <T> T resolve(Class<T> type) {
        if (container.containsKey(type)) {
            return type.cast(container.get(type));
        }

        try {
            Constructor<?>[] constructors = type.getConstructors();
            if (constructors.length > 0)
            {
                Constructor<?> constructor = constructors[0];
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; i++) {
                    parameters[i] = resolve(parameterTypes[i]);
                }

                Object instance = constructor.newInstance(parameters);

                container.put(type, instance);

                return type.cast(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
