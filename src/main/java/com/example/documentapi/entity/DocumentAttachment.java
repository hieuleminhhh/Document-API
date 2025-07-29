package com.example.documentapi.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "document_attachment", schema = "testdb")
@Data
public class DocumentAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    private String fileName;
    private String link;

    @Column(name = "doc_attachment_type_code")
    private String docAttachmentTypeCode;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private Document document;
}
