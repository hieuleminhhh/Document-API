package com.example.documentapi.repository;

import com.example.documentapi.entity.DocumentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentAttachmentRepository extends JpaRepository<DocumentAttachment, UUID> {
}
