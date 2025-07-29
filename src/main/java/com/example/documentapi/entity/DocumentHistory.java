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
@Table(name = "document_history", schema = "testdb")
@Data
public class DocumentHistory {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String content;

    private String reason;

    private String action;

    @Column(name = "message_content")
    private String messageContent;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;
}
