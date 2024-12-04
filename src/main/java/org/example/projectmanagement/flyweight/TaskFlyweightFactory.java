package org.example.projectmanagement.flyweight;

import java.util.HashMap;
import java.util.Map;

public class TaskFlyweightFactory {
    private static final Map<String, TaskFlyweight> flyweights = new HashMap<>();

    public static TaskFlyweight getTaskFlyweight(String name, String projectId, String description, String methodology, String priority, String dueDate) {
        String key = generateKey(name, projectId, description, methodology, priority, dueDate);
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, new ConcreteTaskFlyweight(name, projectId, description, methodology, priority, dueDate));
        }
        return flyweights.get(key);
    }

    private static String generateKey(String name, String projectId, String description, String methodology, String priority, String dueDate) {
        return String.join(":", name, projectId, description, methodology, priority, dueDate);
    }
}