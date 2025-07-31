package com.example.documentapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.documentapi.entity.DocumentHistory;

@Repository
public interface DocumentHistoryRepository extends JpaRepository<DocumentHistory, UUID> {
     List<DocumentHistory> findByDocumentId(UUID documentId);
}
