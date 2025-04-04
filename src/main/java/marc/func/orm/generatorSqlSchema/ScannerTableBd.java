package marc.func.orm.generatorSqlSchema;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ScannerTableBd
{
    public List<Class<?>> scanPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        URL url = getClass().getClassLoader().getResource(path);

        if (url == null) {
            return classes;
        }

        File directory = new File(url.getFile());
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    classes.addAll(scanPackage(packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName.replace("/",".") + "." + file.getName().replace(".class", "");
                    try {
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }
}
