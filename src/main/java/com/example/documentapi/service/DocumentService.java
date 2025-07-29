package com.example.documentapi.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.documentapi.dto.DocumentAttachmentRequest;
import com.example.documentapi.dto.DocumentHistoryRequest;
import com.example.documentapi.dto.DocumentRequest;
import com.example.documentapi.dto.DocumentSearchProjection;
import com.example.documentapi.dto.DocumentSearchRequest;
import com.example.documentapi.entity.Document;
import com.example.documentapi.entity.DocumentAttachment;
import com.example.documentapi.entity.DocumentHistory;
import com.example.documentapi.repository.DocumentAttachmentRepository;
import com.example.documentapi.repository.DocumentHistoryRepository;
import com.example.documentapi.repository.DocumentRepository;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentHistoryRepository historyRepository;
    private final DocumentAttachmentRepository attachmentRepository;

    public DocumentService(DocumentRepository documentRepository,
            DocumentHistoryRepository historyRepository,
            DocumentAttachmentRepository attachmentRepository) {
        this.documentRepository = documentRepository;
        this.historyRepository = historyRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public UUID createDocument(DocumentRequest request) {
        UUID documentId = UUID.randomUUID();

        Document document = new Document();
        document.setId(documentId);
        document.setReceiveTime(LocalDateTime.now());
        document.setLastModifiedTime(LocalDateTime.now());
        document.setTaxCode(request.getTaxCode());
        document.setCompanyName(request.getCompanyName());
        document.setAddress(request.getAddress());
        document.setCompanyPhoneNumber(request.getCompanyPhoneNumber());
        document.setCompanyFax(request.getCompanyFax());
        document.setCompanyEmail(request.getCompanyEmail());
        document.setProvinceCode(request.getProvinceCode());
        document.setWardCode(request.getWardCode());
        document.setDocumentType(request.getDocumentType());
        document.setStatusId(request.getStatusId());
        document.setNswCode(request.getNswCode());

        documentRepository.save(document);
        return documentId;
    }

    public void saveDocumentHistory(UUID documentId, DocumentHistoryRequest request) {
        DocumentHistory history = new DocumentHistory();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        history.setDocument(document);

        history.setTitle(request.getTitle());
        history.setContent(request.getContent());
        history.setReason(request.getReason());
        history.setAction(request.getAction());
        history.setMessageContent(request.getMessageContent());

        historyRepository.save(history);
    }

    public void saveDocumentAttachment(UUID documentId, DocumentAttachmentRequest request) {
        DocumentAttachment attachment = new DocumentAttachment();

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        attachment.setDocument(document);
        attachment.setFileName(request.getFileName());
        attachment.setLink(request.getFileLink());
        attachment.setDocAttachmentTypeCode(request.getAttachmentTypeCode());

        attachmentRepository.save(attachment);
    }

    public Page<DocumentSearchProjection> searchDocuments(DocumentSearchRequest request) {
        return documentRepository.searchDocuments(
                request.getTaxCode(),
                request.getCompanyName(),
                request.getProvinceCode(),
                request.getDocumentType(),
                request.getStatusId() != null ? UUID.fromString(request.getStatusId()) : null,
                PageRequest.of(request.getPage(), request.getSize()));
    }

}
