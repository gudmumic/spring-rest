package com.gudmumic.spring.rest.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Customer {
    private UUID id;
    private Integer version;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
