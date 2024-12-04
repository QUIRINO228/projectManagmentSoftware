package org.example.projectmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Document(collection = "attachments")
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Id
    private String attachmentId;
    private List<String> taskIds;
    private String fileName;
    private String filePath;
    private long fileSize;
    private LocalDate uploadedDate;
}
