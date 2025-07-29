package com.example.documentapi.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DocumentResponse {
    private UUID id;
    private String taxCode;
    private String companyName;
    private String address;
    private String documentType;
    private String provinceCode;
    private UUID statusId;
}
