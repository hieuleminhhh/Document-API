package com.example.documentapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentSearchProjection {
    private UUID id;
    private String taxCode;
    private String companyName;
    private String provinceCode;
    private String documentType;
    private UUID statusId;
    private LocalDateTime receiveTime;

    public DocumentSearchProjection(UUID id, String taxCode, String companyName,
            String provinceCode, String documentType,
            UUID statusId, LocalDateTime receiveTime) {
        this.id = id;
        this.taxCode = taxCode;
        this.companyName = companyName;
        this.provinceCode = provinceCode;
        this.documentType = documentType;
        this.statusId = statusId;
        this.receiveTime = receiveTime;
    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }
}
