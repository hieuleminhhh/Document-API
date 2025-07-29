package com.example.documentapi.repository;

import com.example.documentapi.entity.DocumentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentHistoryRepository extends JpaRepository<DocumentHistory, UUID> {
}
