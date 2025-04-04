package marc.ioc;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class IoCScanner
{

    public static void scanAndRegister(String path, IoCContainer container) throws Exception {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            List<Class<?>> classes = getClassesFromPath(path);
            for (Class<?> clazz : classes) {
                try {
                    Constructor<?> constructor = clazz.getConstructor();
                    Object instance = constructor.newInstance();
                    container.register(clazz, instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<Class<?>> getClassesFromPath(String path) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(path);
        if (directory.exists()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));
            if (files != null) {
                for (File file : files) {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    URL[] urls = { directory.toURI().toURL() };
                    URLClassLoader classLoader = new URLClassLoader(urls);
                    Class<?> clazz = classLoader.loadClass(className);
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }
}
