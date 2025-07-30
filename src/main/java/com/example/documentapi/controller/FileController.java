package com.example.documentapi.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.documentapi.dto.FileDataResponse;
import com.example.documentapi.dto.FileUploadRequest;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${file-server-util.call-api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public FileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody FileUploadRequest request) {
        if (request.getBase64Data() == null || request.getBase64Data().isEmpty()) {
            return ResponseEntity.badRequest().body("Base64 data is empty");
        }
        if (request.getFileName() == null || request.getFileName().isEmpty()) {
            return ResponseEntity.badRequest().body("File name is required");
        }

        try {
            byte[] fileBytes = Base64.getDecoder().decode(request.getBase64Data());
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path filePath = uploadDir.resolve(request.getFileName());
            Files.write(filePath, fileBytes);

            String uploadUrl = baseUrl + "/files/upload";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<FileUploadRequest> requestEntity = new HttpEntity<>(request, headers);

            ResponseEntity<FileDataResponse> response = restTemplate.postForEntity(uploadUrl, requestEntity,
                    FileDataResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Upload failed on remote server");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid base64 data");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving file locally");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error calling remote upload API");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Resource> downloadFile(@RequestParam("id") String fileId) {
        String fileUrl = baseUrl + "/files/get?id=" + fileId;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(fileUrl, HttpMethod.GET, requestEntity, byte[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // Lấy tên file từ header Content-Disposition của server trả về
            String contentDisposition = response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
            String filename = fileId; // fallback

            if (contentDisposition != null && contentDisposition.contains("filename=")) {
                // Tách tên file
                int index = contentDisposition.indexOf("filename=");
                filename = contentDisposition.substring(index + 9).replace("\"", "");
            }

            ByteArrayResource resource = new ByteArrayResource(response.getBody());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }

}
