package com.example.documentapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DocumentRequest {
    @NotBlank(message = "taxCode không được để trống")
    private String taxCode;

    @NotBlank(message = "companyName không được để trống")
    private String companyName;

    private String address;

    @Size(max = 15, message = "Số điện thoại tối đa 15 ký tự")
    private String companyPhoneNumber;

    @Size(max = 15, message = "Số fax tối đa 15 ký tự")
    private String companyFax;

    @Email(message = "Email không hợp lệ")
    private String companyEmail;

    private String provinceCode;

    private String wardCode;

    private String documentType;

    private UUID statusId;

    private String nswCode;
}
