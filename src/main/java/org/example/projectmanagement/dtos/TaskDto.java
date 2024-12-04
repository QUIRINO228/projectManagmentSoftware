package org.example.projectmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.projectmanagement.models.Attachment;

import java.util.Set;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String taskId;
    private String projectId;
    private String name;
    private String assignee;
    private String description;
    private String status;
    private Set<String> assigneeIds;
    private String methodology;
    private String dueDate;
    private String priority;
    private Set<String> attachmentIds;
    private Set<Attachment> attachments;
}
