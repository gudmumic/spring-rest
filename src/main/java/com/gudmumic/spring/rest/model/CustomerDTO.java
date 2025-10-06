package com.gudmumic.spring.rest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
