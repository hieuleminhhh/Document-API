package com.example.documentapi.dto;

import java.io.Serializable;
import java.util.UUID;

public class FileDataResponse implements Serializable {
    private UUID id;
    private String type;
    private String nswCode;
    private String filePath;
    private String originalFileName;
    private String fileExtension;

    // Getter và Setter
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNswCode() { return nswCode; }
    public void setNswCode(String nswCode) { this.nswCode = nswCode; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getOriginalFileName() { return originalFileName; }
    public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }

    public String getFileExtension() { return fileExtension; }
    public void setFileExtension(String fileExtension) { this.fileExtension = fileExtension; }
}
