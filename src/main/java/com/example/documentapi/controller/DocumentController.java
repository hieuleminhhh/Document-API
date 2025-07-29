package com.example.documentapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.documentapi.dto.APIResponseDto;
import com.example.documentapi.dto.DocumentAttachmentRequest;
import com.example.documentapi.dto.DocumentHistoryRequest;
import com.example.documentapi.dto.DocumentRequest;
import com.example.documentapi.dto.DocumentSearchProjection;
import com.example.documentapi.dto.DocumentSearchRequest;
import com.example.documentapi.service.DocumentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String hello() {
        return "Spring Boot is running!";
    }

    @PostMapping
    public ResponseEntity<APIResponseDto> createDocument(@Valid @RequestBody DocumentRequest request) {
        UUID documentId = documentService.createDocument(request);
        return ResponseEntity.ok(new APIResponseDto(true, "Document created successfully", documentId));
    }

    @PostMapping("/history")
    public ResponseEntity<APIResponseDto> saveDocumentHistory(@RequestBody DocumentHistoryRequest request) {
        try {
            if (request.getDocumentId() == null) {
                return ResponseEntity.status(400).body(new APIResponseDto(false, "Document ID is required", null));
            }

            documentService.saveDocumentHistory(request.getDocumentId(), request);

            return ResponseEntity.ok(new APIResponseDto(true, "Document history saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new APIResponseDto(false, "Error saving document history: " + e.getMessage(), null));
        }
    }

    @PostMapping("/attachments")
    public ResponseEntity<APIResponseDto> saveDocumentAttachment(@RequestBody DocumentAttachmentRequest request) {
        try {
            if (request.getDocumentId() == null) {
                return ResponseEntity.status(400).body(new APIResponseDto(false, "Document ID is required", null));
            }
            documentService.saveDocumentAttachment(request.getDocumentId(), request);
            return ResponseEntity.ok(new APIResponseDto(true, "Document attachment saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new APIResponseDto(false, "Error saving document attachment: " + e.getMessage(), null));
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<DocumentSearchProjection>> searchDocuments(@RequestBody DocumentSearchRequest request) {
        return ResponseEntity.ok(documentService.searchDocuments(request));
    }

}
