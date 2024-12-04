package org.example.projectmanagement.flyweight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.projectmanagement.models.Attachment;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class ConcreteTaskFlyweight implements TaskFlyweight {
    private final String name;
    private final String projectId;
    private final String description;
    private final String methodology;
    private String priority;
    private String dueDate;
    private final Set<Attachment> attachments;
    private String assignee;
    private String status;

    public ConcreteTaskFlyweight(String name, String projectId, String description, String methodology, String priority, String dueDate) {
        this.name = name;
        this.projectId = projectId;
        this.description = description;
        this.methodology = methodology;
        this.priority = priority;
        this.dueDate = dueDate;
        this.attachments = new HashSet<>();
    }

    @Override
    public void assignTask(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }
}