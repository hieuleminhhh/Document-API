package com.example.documentapi.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "status", schema = "testdb")
@Data
public class Status {
    @Id
    private UUID id;

    private String name;
    private String code;

}
