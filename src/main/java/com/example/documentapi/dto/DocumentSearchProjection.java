package com.example.documentapi.dto;

import java.util.UUID;

public interface DocumentSearchProjection {
    UUID getId();

    String getTaxCode();

    String getCompanyName();

    String getAddress();

    String getDocumentTypeName();

    String getStatusName();

    String getProvinceName();
}