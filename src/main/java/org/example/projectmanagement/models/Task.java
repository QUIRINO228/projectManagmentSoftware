package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projectmanagement.flyweight.TaskFlyweight;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Document(collection = "tasks")
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String taskId;
    private String name;
    private String projectId;
    private String description;
    private String status;
    private String assignee;
    private String methodology;
    private String priority;
    private String dueDate;
    private Set<Attachment> attachments = new HashSet<>();

    public Task(TaskFlyweight taskFlyweight) {
        this.name = taskFlyweight.getName();
        this.projectId = taskFlyweight.getProjectId();
        this.description = taskFlyweight.getDescription();
        this.status = taskFlyweight.getStatus();
        this.assignee = taskFlyweight.getAssignee();
        this.methodology = taskFlyweight.getMethodology();
        this.priority = taskFlyweight.getPriority();
        this.dueDate = taskFlyweight.getDueDate();
        this.attachments = taskFlyweight.getAttachments();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }
}