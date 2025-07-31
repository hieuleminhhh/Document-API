package com.example.documentapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.documentapi.dto.APIResponseDto;
import com.example.documentapi.dto.DocumentAttachmentRequest;
import com.example.documentapi.dto.DocumentFullResponseDto;
import com.example.documentapi.dto.DocumentHistoryRequest;
import com.example.documentapi.dto.DocumentRequest;
import com.example.documentapi.dto.DocumentSearchProjection;
import com.example.documentapi.dto.DocumentSearchRequest;
import com.example.documentapi.dto.PageResponse;
import com.example.documentapi.service.DocumentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<APIResponseDto> createDocument(@Valid @RequestBody DocumentRequest request) {
        UUID documentId = documentService.createDocument(request);

        APIResponseDto response = new APIResponseDto(true, "Document created successfully", documentId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/history")
    public ResponseEntity<APIResponseDto> saveDocumentHistory(@Valid @RequestBody DocumentHistoryRequest request) {
        try {
            if (request.getDocumentId() == null) {
                return ResponseEntity.status(400)
                        .body(new APIResponseDto(false, "Document ID is required", request));
            }

            documentService.saveDocumentHistory(request.getDocumentId(), request);

            return ResponseEntity.ok(new APIResponseDto(true, "Document history saved successfully", request));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new APIResponseDto(false, "Error saving document history: " + e.getMessage(), request));
        }
    }

    @PostMapping("/attachments")
    public ResponseEntity<APIResponseDto> saveDocumentAttachment(
            @Valid @RequestBody DocumentAttachmentRequest request) {
        try {
            if (request.getDocumentId() == null) {
                return ResponseEntity.status(400)
                        .body(new APIResponseDto(false, "Document ID is required", request));
            }

            documentService.saveDocumentAttachment(request.getDocumentId(), request);

            return ResponseEntity.ok(new APIResponseDto(true, "Document attachment saved successfully", request));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new APIResponseDto(false, "Error saving document attachment: " + e.getMessage(), request));
        }
    }

    @PostMapping("/search")
    public ResponseEntity<APIResponseDto> searchDocuments(@RequestBody DocumentSearchRequest request) {
        try {
            Page<DocumentSearchProjection> searchResultPage = documentService.searchDocuments(request);
            PageResponse<DocumentSearchProjection> pageResponse = new PageResponse<>(searchResultPage);
            return ResponseEntity.ok(new APIResponseDto(true, "Documents searched successfully", pageResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponseDto(false, "Error during document search: " + e.getMessage(), null));
        }
    }

    @GetMapping("/allDocuments")
    public ResponseEntity<APIResponseDto> getAllFullDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<DocumentFullResponseDto> fullDocumentsPage = documentService.getAllFullDocuments(page, size);
            PageResponse<DocumentFullResponseDto> pageResponse = new PageResponse<>(fullDocumentsPage);

            return ResponseEntity.ok(
                    new APIResponseDto(true, "Full documents retrieved successfully", pageResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponseDto(false, "Failed to retrieve full documents: " + e.getMessage(), null));
        }
    }

}
