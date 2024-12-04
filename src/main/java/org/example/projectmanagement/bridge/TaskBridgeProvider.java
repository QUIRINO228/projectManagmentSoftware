package org.example.projectmanagement.bridge;

import org.example.projectmanagement.bridge.impl.AgileTaskBridge;
import org.example.projectmanagement.bridge.impl.KanbanTaskBridge;
import org.example.projectmanagement.bridge.impl.RupTaskBridge;

public class TaskBridgeProvider {

    public static TaskBridge getFactory(String methodology) {
        return switch (methodology) {
            case "Agile" -> new AgileTaskBridge();
            case "Kanban" -> new KanbanTaskBridge();
            case "RUP" -> new RupTaskBridge();
            default -> throw new IllegalArgumentException("Invalid methodology: " + methodology);
        };
    }
}