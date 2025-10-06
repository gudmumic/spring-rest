package com.gudmumic.spring.rest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {
    private UUID id;
    private Integer version;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String name;
    @NotNull
    private BeerStyle style;
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
