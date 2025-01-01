package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    private LocalDate dueDate;
    private Set<Attachment> attachments = new HashSet<>();


    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }
}