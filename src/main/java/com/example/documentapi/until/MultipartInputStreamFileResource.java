package com.example.documentapi.until;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;

public class MultipartInputStreamFileResource extends InputStreamResource {
    private final String filename;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // Nếu không biết size thì trả về -1 để tránh lỗi
    }
}

