package com.example.documentapi.dto;

import java.util.UUID;

public class DocumentSearchProjectionImpl implements DocumentSearchProjection {
    private UUID id;
    private String taxCode;
    private String companyName;
    private String address;
    private String documentTypeName;
    private String statusName;
    private String provinceName;

    public DocumentSearchProjectionImpl(UUID id, String taxCode, String companyName,
                                        String address, String documentTypeName,
                                        String statusName, String provinceName) {
        this.id = id;
        this.taxCode = taxCode;
        this.companyName = companyName;
        this.address = address;
        this.documentTypeName = documentTypeName;
        this.statusName = statusName;
        this.provinceName = provinceName;
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public String getTaxCode() { return taxCode; }

    @Override
    public String getCompanyName() { return companyName; }

    @Override
    public String getAddress() { return address; }

    @Override
    public String getDocumentTypeName() { return documentTypeName; }

    @Override
    public String getStatusName() { return statusName; }

    @Override
    public String getProvinceName() { return provinceName; }
}
