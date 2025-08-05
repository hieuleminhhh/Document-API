package com.example.documentapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.documentapi.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

        @Query("""
                            SELECT d.id FROM Document d
                            WHERE (:taxCode IS NULL OR d.taxCode LIKE %:taxCode%)
                              AND (:companyName IS NULL OR d.companyName LIKE %:companyName%)
                              AND (:address IS NULL OR d.address LIKE %:address%)
                              AND (:companyPhoneNumber IS NULL OR d.companyPhoneNumber LIKE %:companyPhoneNumber%)
                              AND (:companyFax IS NULL OR d.companyFax LIKE %:companyFax%)
                              AND (:companyEmail IS NULL OR d.companyEmail LIKE %:companyEmail%)
                              AND (:provinceCode IS NULL OR d.province.code = :provinceCode)
                              AND (:wardCode IS NULL OR d.ward.code = :wardCode)
                              AND (:documentType IS NULL OR d.documentType.code = :documentType)
                              AND (:statusId IS NULL OR d.status.id = :statusId)
                              AND (:nswCode IS NULL OR d.nswCode LIKE %:nswCode%)
                        """)
        Page<UUID> findDocumentIdsBySearchConditions(
                        String taxCode,
                        String companyName,
                        String address,
                        String companyPhoneNumber,
                        String companyFax,
                        String companyEmail,
                        String provinceCode,
                        String wardCode,
                        String documentType,
                        UUID statusId,
                        String nswCode,
                        Pageable pageable);

        @Query("""
                            SELECT DISTINCT d FROM Document d
                            LEFT JOIN FETCH d.province
                            LEFT JOIN FETCH d.ward
                            LEFT JOIN FETCH d.documentType
                            LEFT JOIN FETCH d.status
                            LEFT JOIN FETCH d.documentHistory
                            LEFT JOIN FETCH d.documentAttachments
                            WHERE d.id IN :ids
                        """)
        List<Document> findAllWithRelationsByIds(List<UUID> ids);
}
