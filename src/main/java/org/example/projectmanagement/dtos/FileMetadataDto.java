package org.example.projectmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
public class FileMetadataDto {
    private String fileName;
    private String filePath;
    private long fileSize;
    private LocalDate uploadedDate;

}