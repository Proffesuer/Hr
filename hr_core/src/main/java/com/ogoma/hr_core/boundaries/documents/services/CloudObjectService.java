package com.ogoma.hr_core.boundaries.documents.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Service
public class CloudObjectService {
    private final MinioClient minioClient;
    private final AmazonS3 amazonS3;

    @Autowired
    public CloudObjectService(MinioClient minioClient, AmazonS3 amazonS3) {
        this.minioClient = minioClient;
        this.amazonS3 = amazonS3;
    }

    public String createObject(String bucketName, MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID().toString();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,fileName+".txt",file.getInputStream(),new ObjectMetadata());
        amazonS3.putObject(putObjectRequest);
        return fileName;
    }

    public void deleteDocument(String bucketName, String fileName) throws Exception {
        RemoveObjectArgs removeObjectArgs= RemoveObjectArgs.builder().object(fileName).bucket(bucketName).build();
        minioClient.removeObject(removeObjectArgs);
    }


}
