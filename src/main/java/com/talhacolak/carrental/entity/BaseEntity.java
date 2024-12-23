package com.talhacolak.carrental.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
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
//  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
//  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updated;
}