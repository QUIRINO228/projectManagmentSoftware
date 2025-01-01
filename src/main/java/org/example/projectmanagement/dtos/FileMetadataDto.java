package org.example.projectmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileMetadataDto {
    private String fileName;
    private String filePath;
    private long fileSize;
    private LocalDate uploadedDate;

}