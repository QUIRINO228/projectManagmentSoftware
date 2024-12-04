package org.example.projectmanagement.services.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectmanagement.client.S3Client;
import org.example.projectmanagement.dtos.FileMetadataDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final S3Client s3Client;
    private final FileValidator fileValidator;

    public List<FileMetadataDto> uploadFilesWithMetadata(List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    try {
                        fileValidator.validateFile(file);

                        String fileExtension = fileValidator.getFileExtension(file.getOriginalFilename());
                        String filePath = UUID.randomUUID() + "." + fileExtension;

                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(file.getSize());
                        metadata.setContentType(file.getContentType());

                        s3Client.uploadFile(filePath, file.getInputStream(), metadata);
                        String fileUrl = s3Client.getPublicUrl(filePath);

                        return new FileMetadataDto(file.getOriginalFilename(), fileUrl, file.getSize(), LocalDate.now());
                    } catch (IOException e) {
                        log.error("Failed to upload file: " + file.getOriginalFilename(), e);
                        throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public String deleteFileByUrl(List<String> urls) throws IOException {
        for (String url : urls) {
            try {
                URL fileUrl = new URL(url);
                String bucketName = extractBucketName(fileUrl);
                String filePath = extractFilePath(fileUrl);
                s3Client.deleteFile(bucketName, filePath);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Invalid URL provided: " + url);
            } catch (AmazonServiceException e) {
                throw new IOException("Failed to delete file from S3: " + e.getMessage(), e);
            }
        }
        return "Files deleted successfully.";
    }

    private String extractBucketName(URL fileUrl) {
        String host = fileUrl.getHost();
        return host.split("\\.")[0];
    }

    private String extractFilePath(URL fileUrl) {
        return fileUrl.getPath().substring(1);
    }
}