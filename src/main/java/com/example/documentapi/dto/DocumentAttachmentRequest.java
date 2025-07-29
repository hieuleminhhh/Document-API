package com.example.documentapi.dto;

import java.util.UUID;

public class DocumentAttachmentRequest {

    private UUID fileId;
    private String fileName;
    private String fileLink;
    private String attachmentTypeCode;

    private UUID documentId;

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getAttachmentTypeCode() {
        return attachmentTypeCode;
    }

    public void setAttachmentTypeCode(String attachmentTypeCode) {
        this.attachmentTypeCode = attachmentTypeCode;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }
}
