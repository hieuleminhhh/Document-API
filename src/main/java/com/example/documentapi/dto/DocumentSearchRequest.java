package com.example.documentapi.dto;

import lombok.Data;

@Data
public class DocumentSearchRequest {
    private String taxCode;
    private String companyName;
    private String provinceCode;
    private String documentType;
    private String statusId;

    private int page = 0;
    private int size = 10;
}
