package com.example.documentapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentHistoryRequest {

    private UUID documentId;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Content must not be blank")
    private String content;

    @Size(max = 255, message = "Reason must not exceed 255 characters")
    private String reason;

    @NotBlank(message = "Action must not be blank")
    private String action;

    @Size(max = 500, message = "Message content must not exceed 500 characters")
    private String messageContent;
}
