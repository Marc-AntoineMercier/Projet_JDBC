package marc.func.orm.generatorSqlSchema;

import marc.func.orm.request.UpdateRequest;
import marc.func.orm.table.annotation.Table;
import marc.func.orm.table.annotation.relation.ManyToOne;
import marc.func.orm.table.annotation.relation.OneToOne;
import marc.func.orm.table.generator.GeneratorTableRequest;

import java.lang.reflect.Field;
import java.util.*;

import static marc.app.Application.container;

public class InitializeTableDb
{

    private ScannerTableBd tableBd;
    private UpdateRequest updateRequest;
    private GeneratorTableRequest generatorTableRequest;

    public InitializeTableDb()
    {
        this.updateRequest = container.resolve(UpdateRequest.class);
        this.generatorTableRequest = container.resolve(GeneratorTableRequest.class);
        this.tableBd = container.resolve(ScannerTableBd.class);
    }

    static class Node
    {
        String className;
        List<Node> dependencies;

        Node(String className)
        {
            this.className = className;
            this.dependencies = new ArrayList<>();
        }
    }

    public void initializeTablesAutomatically(String path) throws Exception
    {
        Map<Class<?>, Integer> listPriority = setPriority(path);
        Map<Class<?>, Integer> temp = new HashMap<>(listPriority);

        dropTableForDataBase(temp);

        createTableForDataBase(listPriority);
    }

    private void dropTableForDataBase(Map<Class<?>, Integer> listPriority) throws Exception
    {
        while (!listPriority.isEmpty())
        {
            Class<?> lowestPriority = getClassWithLowestPriority(listPriority);

            updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(lowestPriority));

            listPriority.remove(lowestPriority);
        }
    }

    private void createTableForDataBase(Map<Class<?>, Integer> listPriority) throws Exception
    {
        while (!listPriority.isEmpty())
        {
            Class<?> highestPriorityClass = getClassWithHighestPriority(listPriority);

            updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(highestPriorityClass));

            listPriority.remove(highestPriorityClass);
        }
    }

    private Class<?> getClassWithLowestPriority(Map<Class<?>, Integer> tableCreated)
    {
        if (tableCreated == null || tableCreated.isEmpty())
        {
            return null;
        }

        Class<?> classWithHighestPriority = null;
        int highestPriority = Integer.MAX_VALUE;

        for (Map.Entry<Class<?>, Integer> entry : tableCreated.entrySet())
        {
            if (entry.getValue() < highestPriority)
            {
                highestPriority = entry.getValue();
                classWithHighestPriority = entry.getKey();
            }
        }

        return classWithHighestPriority;
    }

    private Class<?> getClassWithHighestPriority(Map<Class<?>, Integer> tableCreated)
    {
        if (tableCreated == null || tableCreated.isEmpty())
        {
            return null;
        }

        Class<?> classWithHighestPriority = null;
        int highestPriority = Integer.MIN_VALUE;

        for (Map.Entry<Class<?>, Integer> entry : tableCreated.entrySet())
        {
            if (entry.getValue() > highestPriority)
            {
                highestPriority = entry.getValue();
                classWithHighestPriority = entry.getKey();
            }
        }

        return classWithHighestPriority;
    }

    private Map<Class<?>, Integer> setPriority(String packageName) throws Exception
    {
        List<Class<?>> entityClasses = tableBd.scanPackage(packageName);
        Map<Class<?>, Node> classNodes = new HashMap<>();

        for (Class<?> entityClass : entityClasses)
        {
            if (entityClass.isAnnotationPresent(Table.class))
            {
                Node node = new Node(entityClass.getName());
                classNodes.put(entityClass, node);
            }
        }

        for (Class<?> entityClass : entityClasses)
        {
            Node classNode = classNodes.get(entityClass);
            for (Field field : entityClass.getDeclaredFields())
            {
                if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class))
                {
                    Class<?> relatedClass = field.getType();
                    Node relatedNode = classNodes.get(relatedClass);
                    classNode.dependencies.add(relatedNode);
                }
            }
        }

        List<Node> sortedNodes = topologicalSort(classNodes);

        Map<Class<?>, Integer> tableCreated = new HashMap<>();
        int priority = 1;
        for (Node node : sortedNodes)
        {
            tableCreated.put(Class.forName(node.className), priority++);
        }

        return tableCreated;
    }


    private List<Node> topologicalSort(Map<Class<?>, Node> classNodes) throws Exception
    {
        Map<Node, Integer> inDegree = new HashMap<>();
        for (Node node : classNodes.values())
        {
            inDegree.put(node, 0);
        }

        for (Node node : classNodes.values())
        {
            for (Node dependency : node.dependencies)
            {
                inDegree.put(dependency, inDegree.get(dependency) + 1);
            }
        }

        Queue<Node> queue = new LinkedList<>();
        for (Node node : inDegree.keySet())
        {
            if (inDegree.get(node) == 0)
            {
                queue.add(node);
            }
        }

        List<Node> sortedNodes = new ArrayList<>();
        while (!queue.isEmpty())
        {
            Node node = queue.poll();
            sortedNodes.add(node);
            for (Node dependency : node.dependencies)
            {
                inDegree.put(dependency, inDegree.get(dependency) - 1);
                if (inDegree.get(dependency) == 0)
                {
                    queue.add(dependency);
                }
            }
        }

        if (sortedNodes.size() != classNodes.size())
        {
            throw new Exception("Graph has a cycle! Circular dependencies detected in ");
        }

        return sortedNodes;
    }
}
