package com.example.documentapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "province", schema = "testdb")
@Data
public class Province {

    @Id
    @Column(length = 25)
    private String code;

    private String name;
}
