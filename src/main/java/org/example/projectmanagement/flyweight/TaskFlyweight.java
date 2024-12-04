package org.example.projectmanagement.flyweight;

import org.example.projectmanagement.models.Attachment;

import java.util.Set;

public interface TaskFlyweight {
    void assignTask(String assignee);
    void setStatus(String status);
    void setPriority(String priority);
    void setDueDate(String dueDate);
    void addAttachment(Attachment attachment);
    Set<Attachment> getAttachments();

    String getName();
    String getProjectId();
    String getDescription();
    String getStatus();
    String getAssignee();
    String getMethodology();
    String getPriority();
    String getDueDate();
}