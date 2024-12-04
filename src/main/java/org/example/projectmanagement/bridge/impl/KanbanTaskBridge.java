package org.example.projectmanagement.bridge.impl;

import org.example.projectmanagement.bridge.AbstractTaskBridge;
import org.example.projectmanagement.models.enums.KanbanColumn;

public class KanbanTaskBridge extends AbstractTaskBridge {

    public KanbanTaskBridge() {
        this.methodology = "Kanban";
    }

    @Override
    protected String getInitialStatus() {
        return KanbanColumn.BACKLOG.name();
    }
}