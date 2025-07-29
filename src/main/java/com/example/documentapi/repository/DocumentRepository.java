package com.example.documentapi.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.documentapi.dto.DocumentSearchProjection;
import com.example.documentapi.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Query(value = """
            SELECT d.id AS id,
                   d.tax_code AS taxCode,
                   d.company_name AS companyName,
                   d.address AS address,
                   dt.name AS documentTypeName,
                   s.name AS statusName,
                   p.name AS provinceName
            FROM testdb.document d
            LEFT JOIN testdb.document_type dt ON d.document_type = dt.code
            LEFT JOIN testdb.status s ON d.status_id = s.id
            LEFT JOIN testdb.province p ON d.province_code = p.code
            WHERE (:taxCode IS NULL OR d.tax_code = :taxCode)
              AND (:companyName IS NULL OR LOWER(d.company_name) LIKE LOWER(CONCAT('%', :companyName, '%')))
              AND (:provinceCode IS NULL OR d.province_code = :provinceCode)
              AND (:documentType IS NULL OR d.document_type = :documentType)
              AND (:statusId IS NULL OR d.status_id = :statusId)
            """, nativeQuery = true)
    Page<DocumentSearchProjection> searchDocuments(
            String taxCode,
            String companyName,
            String provinceCode,
            String documentType,
            UUID statusId,
            Pageable pageable);

}