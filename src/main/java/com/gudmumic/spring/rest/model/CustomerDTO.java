package com.gudmumic.spring.rest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private UUID id;
    private Integer version;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private String country;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
