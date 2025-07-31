package com.example.documentapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFullResponseDto {
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
    private String documentType;
    private UUID statusId;
    private String nswCode;

    private List<DocumentHistoryRequest> history;
    private List<DocumentAttachmentRequest> attachments;
}
