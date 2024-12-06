package org.example.projectmanagement.bridge;

import org.example.projectmanagement.bridge.impl.AgileTaskBridge;
import org.example.projectmanagement.bridge.impl.KanbanTaskBridge;
import org.example.projectmanagement.bridge.impl.RupTaskBridge;

public class TaskBridgeProvider {

    public static TaskBridge getBridge(String methodology, TaskBridge taskBridge) {
        return switch (methodology) {
            case "Agile" -> new AgileTaskBridge(taskBridge);
            case "Kanban" -> new KanbanTaskBridge(taskBridge);
            case "RUP" -> new RupTaskBridge(taskBridge);
            default -> throw new IllegalArgumentException("Invalid methodology: " + methodology);
        };
    }
}