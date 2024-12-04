package org.example.projectmanagement.factory;

import org.example.projectmanagement.factory.impl.AgileTaskFactory;
import org.example.projectmanagement.factory.impl.KanbanTaskFactory;
import org.example.projectmanagement.factory.impl.RupTaskFactory;

public class TaskFactoryProvider {

    public static TaskFactory getFactory(String methodology) {
        return switch (methodology) {
            case "Agile" -> new AgileTaskFactory();
            case "Kanban" -> new KanbanTaskFactory();
            case "RUP" -> new RupTaskFactory();
            default -> throw new IllegalArgumentException("Invalid methodology: " + methodology);
        };
    }
}