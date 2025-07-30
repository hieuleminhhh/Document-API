package com.example.documentapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponseDto {
    private boolean success;
    private String message;
    private Object data;
}
