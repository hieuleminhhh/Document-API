package com.example.documentapi.dto;

import lombok.Data;

@Data
public class DocumentSearchRequest {
    private String taxCode;
    private String companyName;
    private String address;
    private String companyPhoneNumber;
    private String companyFax;
    private String companyEmail;
    private String provinceCode;
    private String wardCode;
    private String documentType;
    private String statusId;
    private String nswCode;
    private int page = 0;
    private int size = 10;
}

