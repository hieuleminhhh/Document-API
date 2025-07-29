package com.example.documentapi.dto;

public class FileUploadRequest {
    private String documentType;
    private String nswCode;
    private String base64Data;
    private String fileName;
    private Boolean isExportToWord;

    // getter - setter
    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getNswCode() { return nswCode; }
    public void setNswCode(String nswCode) { this.nswCode = nswCode; }

    public String getBase64Data() { return base64Data; }
    public void setBase64Data(String base64Data) { this.base64Data = base64Data; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public Boolean getIsExportToWord() { return isExportToWord; }
    public void setIsExportToWord(Boolean isExportToWord) { this.isExportToWord = isExportToWord; }
}