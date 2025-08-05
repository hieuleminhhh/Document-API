package com.example.documentapi.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.documentapi.dto.DocumentAttachmentRequest;
import com.example.documentapi.dto.DocumentFullResponseDto;
import com.example.documentapi.dto.DocumentHistoryRequest;
import com.example.documentapi.dto.DocumentRequest;
import com.example.documentapi.dto.DocumentSearchProjection;
import com.example.documentapi.dto.DocumentSearchRequest;
import com.example.documentapi.entity.Document;
import com.example.documentapi.entity.DocumentAttachment;
import com.example.documentapi.entity.DocumentHistory;
import com.example.documentapi.entity.DocumentType;
import com.example.documentapi.entity.Province;
import com.example.documentapi.entity.Status;
import com.example.documentapi.entity.Ward;
import com.example.documentapi.repository.DocumentAttachmentRepository;
import com.example.documentapi.repository.DocumentHistoryRepository;
import com.example.documentapi.repository.DocumentRepository;
import com.example.documentapi.repository.DocumentTypeRepository;
import com.example.documentapi.repository.ProvinceRepository;
import com.example.documentapi.repository.StatusRepository;
import com.example.documentapi.repository.WardRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentHistoryRepository historyRepository;

    @Autowired
    private DocumentAttachmentRepository attachmentRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private StatusRepository statusRepository;

    // Create Document method
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

        Province province = provinceRepository.findByCode(request.getProvinceCode());
        if (province != null) {
            document.setProvince(province);
        }

        Ward ward = wardRepository.findByCode(request.getWardCode());
        if (ward != null) {
            document.setWard(ward);
        }

        DocumentType documentType = documentTypeRepository.findByCode(request.getDocumentType());
        if (documentType != null) {
            document.setDocumentType(documentType);
        }

        Status status = statusRepository.findById(request.getStatusId()).orElse(null);
        if (status != null) {
            document.setStatus(status);
        }

        document.setNswCode(request.getNswCode());

        documentRepository.save(document);
        return documentId;
    }

    // Save Document History method
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
        UUID statusId = null;
        if (request.getStatusId() != null && !request.getStatusId().isEmpty()) {
            try {
                statusId = UUID.fromString(request.getStatusId());
            } catch (IllegalArgumentException e) {
                // ignore
            }
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Page<UUID> documentIdPage = documentRepository.findDocumentIdsBySearchConditions(
                request.getTaxCode(),
                request.getCompanyName(),
                request.getAddress(),
                request.getCompanyPhoneNumber(),
                request.getCompanyFax(),
                request.getCompanyEmail(),
                request.getProvinceCode(),
                request.getWardCode(),
                request.getDocumentType(),
                statusId,
                request.getNswCode(),
                pageable);

        List<UUID> ids = documentIdPage.getContent();
        if (ids.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<Document> documents = documentRepository.findAllWithRelationsByIds(ids);

        List<DocumentSearchProjection> dtos = documents.stream()
                .map(doc -> new DocumentSearchProjection(
                        doc.getId(),
                        doc.getTaxCode(),
                        doc.getCompanyName(),
                        doc.getProvince() != null ? doc.getProvince().getCode() : null,
                        doc.getDocumentType() != null ? doc.getDocumentType().getCode() : null,
                        doc.getStatus() != null ? doc.getStatus().getId() : null,
                        doc.getReceiveTime()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, documentIdPage.getTotalElements());
    }

    public Page<DocumentFullResponseDto> getAllFullDocuments(DocumentSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        UUID statusId = null;
        if (request.getStatusId() != null && !request.getStatusId().isEmpty()) {
            try {
                statusId = UUID.fromString(request.getStatusId());
            } catch (IllegalArgumentException ignored) {
            }
        }

        Page<UUID> documentIdPage = documentRepository.findDocumentIdsBySearchConditions(
                request.getTaxCode(),
                request.getCompanyName(),
                request.getAddress(),
                request.getCompanyPhoneNumber(),
                request.getCompanyFax(),
                request.getCompanyEmail(),
                request.getProvinceCode(),
                request.getWardCode(),
                request.getDocumentType(),
                statusId,
                request.getNswCode(),
                pageable);

        List<UUID> ids = documentIdPage.getContent();
        if (ids.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<Document> documents = documentRepository.findAllWithRelationsByIds(ids);

        Map<UUID, Document> documentMap = documents.stream()
                .collect(Collectors.toMap(Document::getId, Function.identity()));

        List<DocumentFullResponseDto> dtoList = ids.stream()
                .map(documentMap::get)
                .filter(Objects::nonNull)
                .map(this::convertToFullDto)
                .toList();

        return new PageImpl<>(dtoList, pageable, documentIdPage.getTotalElements());
    }

    private DocumentFullResponseDto convertToFullDto(Document doc) {
        UUID documentId = doc.getId();

        List<DocumentHistoryRequest> historyDtos = doc.getDocumentHistory().stream()
                .map(history -> {
                    DocumentHistoryRequest dto = new DocumentHistoryRequest();
                    dto.setDocumentId(documentId);
                    dto.setHistoryId(history.getId());
                    dto.setTitle(history.getTitle());
                    dto.setContent(history.getContent());
                    dto.setReason(history.getReason());
                    dto.setAction(history.getAction());
                    dto.setMessageContent(history.getMessageContent());
                    return dto;
                }).toList();

        List<DocumentAttachmentRequest> attachmentDtos = doc.getDocumentAttachments().stream()
                .map(att -> {
                    DocumentAttachmentRequest dto = new DocumentAttachmentRequest();
                    dto.setFileId(att.getId());
                    dto.setDocumentId(documentId);
                    dto.setFileName(att.getFileName());
                    dto.setFileLink(att.getLink());
                    dto.setAttachmentTypeCode(att.getDocAttachmentTypeCode());
                    return dto;
                }).toList();

        DocumentFullResponseDto fullDto = new DocumentFullResponseDto();
        fullDto.setId(documentId);
        fullDto.setReceiveTime(doc.getReceiveTime());
        fullDto.setLastModifiedTime(doc.getLastModifiedTime());
        fullDto.setTaxCode(doc.getTaxCode());
        fullDto.setCompanyName(doc.getCompanyName());
        fullDto.setAddress(doc.getAddress());
        fullDto.setCompanyPhoneNumber(doc.getCompanyPhoneNumber());
        fullDto.setCompanyFax(doc.getCompanyFax());
        fullDto.setCompanyEmail(doc.getCompanyEmail());

        fullDto.setProvinceCode(doc.getProvince() != null ? doc.getProvince().getCode() : null);
        fullDto.setWardCode(doc.getWard() != null ? doc.getWard().getCode() : null);
        fullDto.setDocumentType(doc.getDocumentType() != null ? doc.getDocumentType().getCode() : null);
        fullDto.setStatusId(doc.getStatus() != null ? doc.getStatus().getId() : null);

        fullDto.setNswCode(doc.getNswCode());
        fullDto.setHistory(historyDtos);
        fullDto.setAttachments(attachmentDtos);

        return fullDto;
    }

}
