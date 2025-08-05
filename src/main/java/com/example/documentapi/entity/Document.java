package com.example.documentapi.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "document", schema = "testdb")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // ✅ Chỉ dùng trường được đánh dấu
public class Document {

    @Id
    @Column(nullable = false)
    @EqualsAndHashCode.Include // ✅ Chỉ dùng id để sinh equals & hashCode
    private UUID id;

    private LocalDateTime receiveTime;
    private LocalDateTime lastModifiedTime;

    private String taxCode;
    private String companyName;
    private String address;
    private String companyPhoneNumber;
    private String companyFax;
    private String companyEmail;

    @ManyToOne
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "ward_code", referencedColumnName = "code")
    private Ward ward;

    @ManyToOne
    @JoinColumn(name = "document_type", referencedColumnName = "code")
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @Column(length = 25, nullable = false)
    private String nswCode;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DocumentHistory> documentHistory = new HashSet<>();

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DocumentAttachment> documentAttachments = new HashSet<>();
}
