package com.example.documentapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ward", schema = "testdb")
@Data
public class Ward {

    @Id
    @Column(length = 25)
    private String code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Province province;
}
