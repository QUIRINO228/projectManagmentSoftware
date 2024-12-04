package org.example.projectmanagement.services.attachment;

import lombok.AllArgsConstructor;
import org.example.projectmanagement.dtos.FileMetadataDto;
import org.example.projectmanagement.models.Attachment;
import org.example.projectmanagement.services.file.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final FileService fileService;

    @Override
    public List<Attachment> createAttachmentsFromFiles(List<MultipartFile> files) throws IOException {
        List<FileMetadataDto> fileMetadataList = fileService.uploadFilesWithMetadata(files);
        return createAttachmentsFromMetadata(fileMetadataList);
    }

    public List<Attachment> createAttachmentsFromMetadata(List<FileMetadataDto> fileMetadataList) {
        return fileMetadataList.stream()
                .map(metadata -> Attachment.builder()
                        .fileName(metadata.getFileName())
                        .filePath(metadata.getFilePath())
                        .fileSize(metadata.getFileSize())
                        .uploadedDate(metadata.getUploadedDate())
                        .build())
                .collect(Collectors.toList());
    }
}