package org.example.projectmanagement.factory.impl;

import org.example.projectmanagement.models.enums.KanbanColumn;

public class KanbanTaskFactory extends AbstractTaskFactory {

    public KanbanTaskFactory() {
        this.methodology = "Kanban";
    }

    @Override
    protected String getInitialStatus() {
        return KanbanColumn.BACKLOG.name();
    }
}