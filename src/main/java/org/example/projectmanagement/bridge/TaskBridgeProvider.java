package org.example.projectmanagement.bridge;

import org.example.projectmanagement.bridge.impl.AgileTaskBridge;
import org.example.projectmanagement.bridge.impl.KanbanTaskBridge;
import org.example.projectmanagement.bridge.impl.RupTaskBridge;

public class TaskBridgeProvider {

    private static TaskBridge taskBridge;

    public static void setTaskBridge(TaskBridge taskBridge) {
        TaskBridgeProvider.taskBridge = taskBridge;
    }

    public static TaskBridge getBridge(String methodology) {
        if (taskBridge == null) {
            throw new IllegalStateException("TaskBridge is not set. Please set it before calling getBridge.");
        }
        return switch (methodology) {
            case "Agile" -> new AgileTaskBridge(taskBridge);
            case "Kanban" -> new KanbanTaskBridge(taskBridge);
            case "RUP" -> new RupTaskBridge(taskBridge);
            default -> throw new IllegalArgumentException("Invalid methodology: " + methodology);
        };
    }
}
