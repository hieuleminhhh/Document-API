package com.example.documentapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentRequest {

    @NotBlank(message = "Tax code must not be blank")
    @Size(max = 50, message = "Tax code must not exceed 50 characters")
    private String taxCode;

    @NotBlank(message = "Company name must not be blank")
    @Size(max = 255, message = "Company name must not exceed 255 characters")
    private String companyName;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    private String companyPhoneNumber;

    @Size(max = 15, message = "Fax number must not exceed 15 characters")
    private String companyFax;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String companyEmail;

    @NotBlank(message = "Province code must not be blank")
    @Size(max = 20, message = "Province code must not exceed 20 characters")
    private String provinceCode;

    @NotBlank(message = "Ward code must not be blank")
    @Size(max = 20, message = "Ward code must not exceed 20 characters")
    private String wardCode;

    @NotBlank(message = "Document type must not be blank")
    @Size(max = 50, message = "Document type must not exceed 50 characters")
    private String documentType;

    @NotNull(message = "Status ID must not be null")
    private UUID statusId;

    @NotBlank(message = "NSW code must not be blank")
    @Size(max = 50, message = "NSW code must not exceed 50 characters")
    private String nswCode;
}
