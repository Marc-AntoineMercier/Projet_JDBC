package marc.database.generatorSqlSchema;

import marc.database.request.UpdateRequest;
import marc.database.table.annotation.Table;
import marc.database.table.annotation.relation.ManyToOne;
import marc.database.table.annotation.relation.OneToOne;
import marc.database.table.generator.GeneratorTableRequest;

import java.lang.reflect.Field;
import java.util.*;

import static marc.Application.container;

public class InitializeTableDb
{

    private ScannerTableBd tableBd = new ScannerTableBd();
    private UpdateRequest updateRequest;
    private GeneratorTableRequest generatorTableRequest;

    public InitializeTableDb()
    {
        this.updateRequest = container.resolve(UpdateRequest.class);
        this.generatorTableRequest = container.resolve(GeneratorTableRequest.class);
    }

    // Représente une classe dans le graph
    static class Node {
        String className;
        List<Node> dependencies;

        Node(String className) {
            this.className = className;
            this.dependencies = new ArrayList<>();
        }
    }

    public void initializeTablesAutomatically(String path) throws Exception {
        Map<Class<?>, Integer> listPriority = setPriority(path);

        Iterator<Class<?>> iterator = listPriority.keySet().iterator();

        while (iterator.hasNext()) {
            Class<?> type = iterator.next();

            Class<?> highestPriority = getClassWithHighestPriority(listPriority);

            updateRequest.executeStatement(generatorTableRequest.generatedDropTableRequest(highestPriority));
            updateRequest.executeStatement(generatorTableRequest.generatedCreateTableRequest(highestPriority));

            iterator.remove();
        }
    }

    private Class<?> getClassWithHighestPriority(Map<Class<?>, Integer> tableCreated) {
        if (tableCreated == null || tableCreated.isEmpty()) {
            return null;
        }

        Class<?> classWithHighestPriority = null;
        int highestPriority = Integer.MIN_VALUE;

        for (Map.Entry<Class<?>, Integer> entry : tableCreated.entrySet()) {
            if (entry.getValue() > highestPriority) {
                highestPriority = entry.getValue();
                classWithHighestPriority = entry.getKey();
            }
        }

        return classWithHighestPriority;
    }

    private Map<Class<?>, Integer> setPriority(String packageName) throws Exception {
        List<Class<?>> entityClasses = tableBd.scanPackage(packageName);
        Map<Class<?>, Node> classNodes = new HashMap<>();

        for (Class<?> entityClass : entityClasses) {
            if (entityClass.isAnnotationPresent(Table.class)) {
                Node node = new Node(entityClass.getName());
                classNodes.put(entityClass, node);
            }
        }

        for (Class<?> entityClass : entityClasses) {
            Node classNode = classNodes.get(entityClass);
            for (Field field : entityClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(ManyToOne.class)) {
                    Class<?> relatedClass = field.getType();
                    Node relatedNode = classNodes.get(relatedClass);
                    classNode.dependencies.add(relatedNode);
                }
            }
        }

        List<Node> sortedNodes = topologicalSort(classNodes);

        Map<Class<?>, Integer> tableCreated = new HashMap<>();
        int priority = 1;
        for (Node node : sortedNodes) {
            tableCreated.put(Class.forName(node.className), priority++);
        }


        return tableCreated;
    }


    private List<Node> topologicalSort(Map<Class<?>, Node> classNodes) throws Exception {
        Map<Node, Integer> inDegree = new HashMap<>();
        for (Node node : classNodes.values()) {
            inDegree.put(node, 0);
        }

        for (Node node : classNodes.values()) {
            for (Node dependency : node.dependencies) {
                inDegree.put(dependency, inDegree.get(dependency) + 1);
            }
        }

        Queue<Node> queue = new LinkedList<>();
        for (Node node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                queue.add(node);
            }
        }

        List<Node> sortedNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            sortedNodes.add(node);
            for (Node dependency : node.dependencies) {
                inDegree.put(dependency, inDegree.get(dependency) - 1);
                if (inDegree.get(dependency) == 0) {
                    queue.add(dependency);
                }
            }
        }

        if (sortedNodes.size() != classNodes.size()) {
            throw new Exception("Graph has a cycle! Circular dependencies detected.");
        }

        return sortedNodes;
    }
}
