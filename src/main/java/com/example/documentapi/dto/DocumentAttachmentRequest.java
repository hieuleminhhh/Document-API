package com.example.documentapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DocumentAttachmentRequest {

    private UUID fileId;

    @NotBlank(message = "File name must not be blank")
    private String fileName;

    @NotBlank(message = "File link must not be blank")
    private String fileLink;

    @NotBlank(message = "Attachment type code must not be blank")
    private String attachmentTypeCode;

    private UUID documentId;
}
