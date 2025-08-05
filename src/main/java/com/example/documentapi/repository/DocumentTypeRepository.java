package com.example.documentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.documentapi.entity.DocumentType;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, String> {
    DocumentType findByCode(String code);
}
