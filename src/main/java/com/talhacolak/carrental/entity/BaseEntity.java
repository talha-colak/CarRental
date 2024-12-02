package com.talhacolak.carrental.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36)
    private UUID id;

    // for soft deleted purposes
    private boolean deleted;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate created;

    @JsonFormat(pattern = "dd-mm-yyyy")
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDate updated;
}