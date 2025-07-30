package com.example.documentapi;

import com.example.documentapi.controller.FileController;
import com.example.documentapi.dto.FileDataResponse;
import com.example.documentapi.dto.FileUploadRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void uploadFile_Success() throws Exception {
        FileUploadRequest request = new FileUploadRequest();
        request.setFileName("test.txt");
        request.setBase64Data(Base64.getEncoder().encodeToString("Hello".getBytes()));

        FileDataResponse mockResponse = new FileDataResponse();
        mockResponse.setId(UUID.randomUUID());
        mockResponse.setOriginalFileName("test.txt");

        ResponseEntity<FileDataResponse> restResponse = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        Mockito.when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(FileDataResponse.class)))
                .thenReturn(restResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/files/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalFileName").value("test.txt"));
    }

    @Test
    public void uploadFile_MissingFileName() throws Exception {
        FileUploadRequest request = new FileUploadRequest();
        request.setBase64Data(Base64.getEncoder().encodeToString("Test".getBytes()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/files/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("File name is required"));
    }

    @Test
    public void uploadFile_InvalidBase64() throws Exception {
        FileUploadRequest request = new FileUploadRequest();
        request.setFileName("bad.txt");
        request.setBase64Data("NotBase64!");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/files/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid base64 data"));
    }

    @Test
    public void downloadFile_Success() throws Exception {
        String fileId = UUID.randomUUID().toString();
        byte[] content = "Hello world".getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"hello.txt\"");
        ResponseEntity<byte[]> mockResponse = new ResponseEntity<>(content, headers, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(contains("/files/get?id=" + fileId), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(byte[].class)))
                .thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/files/get?id=" + fileId))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"hello.txt\""))
                .andExpect(content().bytes(content));
    }

    @Test
    public void downloadFile_NotFound() throws Exception {
        String fileId = UUID.randomUUID().toString();

        ResponseEntity<byte[]> mockResponse = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        Mockito.when(restTemplate.exchange(contains("/files/get?id=" + fileId), eq(HttpMethod.GET),
                any(HttpEntity.class), eq(byte[].class)))
                .thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/files/get?id=" + fileId))
                .andExpect(status().isNotFound());
    }
}
