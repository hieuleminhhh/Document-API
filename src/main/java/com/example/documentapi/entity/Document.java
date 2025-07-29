package com.example.documentapi.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "document", schema = "testdb")
@Data
public class Document {

    @Id
    @Column(nullable = false)
    private UUID id;

    private LocalDateTime receiveTime;
    private LocalDateTime lastModifiedTime;

    private String taxCode;
    private String companyName;
    private String address;
    private String companyPhoneNumber;
    private String companyFax;
    private String companyEmail;

    private String provinceCode;
    private String wardCode;

    @Column(length = 25)
    private String documentType;

    private UUID statusId;

    @Column(length = 25, nullable = false)
    private String nswCode;
}
