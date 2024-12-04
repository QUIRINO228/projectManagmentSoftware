package org.example.projectmanagement.factory;

import org.example.projectmanagement.factory.impl.AgileProjectFactory;
import org.example.projectmanagement.factory.impl.KanbanProjectFactory;
import org.example.projectmanagement.factory.impl.RupProjectFactory;

public class ProjectFactoryProvider {

    public static ProjectFactory getFactory(String methodology) {
        return switch (methodology.toLowerCase()) {
            case "agile" -> new AgileProjectFactory();
            case "kanban" -> new KanbanProjectFactory();
            case "rup" -> new RupProjectFactory();
            default -> throw new IllegalArgumentException("Unknown methodology: " + methodology);
        };
    }
}
