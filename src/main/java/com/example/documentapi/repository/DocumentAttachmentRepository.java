package com.example.documentapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.documentapi.entity.DocumentAttachment;

@Repository
public interface DocumentAttachmentRepository extends JpaRepository<DocumentAttachment, UUID> {
    List<DocumentAttachment> findByDocumentId(UUID documentId);
}
