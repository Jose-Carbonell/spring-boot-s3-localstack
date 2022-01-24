package com.example.demo.controllers;

import java.io.File;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


class PostTestEntity {
    public String name;
    public String lastName;
}

@RestController
@RequestMapping("/test")
class resourceNameController {
    Logger logger = LoggerFactory.getLogger(resourceNameController.class);
    private AWSCredentials credentials;
    private AmazonS3 s3Client;
    resourceNameController() {
        this.credentials = new BasicAWSCredentials("1234", "1234");
        this.s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(("http://localhost:4566"), "us-east-1")).withPathStyleAccessEnabled(true).build();
    }   

    @GetMapping
    public ResponseEntity<ObjectListing> GetTest() {
        var objectList = this.s3Client.listObjects("{your_bucket_name}");
        return ResponseEntity.status(200).body(objectList);
    }

    @PostMapping
    public ResponseEntity<String> PostTest(@RequestBody PostTestEntity item) {
        try {
            Gson json = new Gson();
            String jsonData = json.toJson(item);
            var response = this.uploadData(jsonData, ".json");
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // Upload file to s3 bucket
    private String uploadFile(File file) {
        String fileName = System.currentTimeMillis() + "_" + file.getName();
        try {
            this.s3Client.putObject("{your_bucket_name}", fileName, file);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return "File uploaded: " + fileName;
    }

    // upload data to s3 bucket
    private String uploadData(String data, String extension) {
        String fileName = System.currentTimeMillis() + extension;
        try {
            this.s3Client.putObject("{your_bucket_name}", fileName, data);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return "File uploaded: " + fileName;
    }
}