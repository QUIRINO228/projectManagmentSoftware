package org.example.projectmanagement.services.attachment;

import org.example.projectmanagement.models.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {
    List<Attachment> createAttachmentsFromFiles(List<MultipartFile> files) throws IOException;

}