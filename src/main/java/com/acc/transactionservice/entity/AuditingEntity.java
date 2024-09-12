package com.acc.transactionservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity<T> implements Serializable {

    @CreatedBy
    @Column(updatable = false)
    protected T createdBy;

    @CreatedDate
    @Column(updatable = false)
    protected Instant createdDate = Instant.now();

    @LastModifiedDate
    protected Instant lastModifiedDate = Instant.now();

}
