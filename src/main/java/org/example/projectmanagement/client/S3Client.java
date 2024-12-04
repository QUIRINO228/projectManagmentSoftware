package org.example.projectmanagement.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;


@Service
public class S3Client {

    private final AmazonS3 amazonS3;

    @Value("${amazons3.bucket_name}")
    private String bucketName;

    public S3Client(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadFile(String key, InputStream inputStream, ObjectMetadata metadata) {
        amazonS3.putObject(new PutObjectRequest(bucketName, key, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void deleteFile(String bucketName, String filePath) {
        amazonS3.deleteObject(bucketName, filePath);
    }

    public String getPublicUrl(String filePath) {
        return amazonS3.getUrl(this.bucketName, filePath).toString();
    }
}