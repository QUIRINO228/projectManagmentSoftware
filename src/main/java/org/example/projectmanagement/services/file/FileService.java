package org.example.projectmanagement.services.file;

import org.example.projectmanagement.dtos.FileMetadataDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<FileMetadataDto> uploadFilesWithMetadata(List<MultipartFile> files) throws IOException;
    String deleteFileByUrl(List<String> urls) throws IOException;
}