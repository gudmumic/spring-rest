package com.gudmumic.spring.rest.entities;

import com.gudmumic.spring.rest.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
    @Version
    private Integer version;
    @NotBlank
    @NotNull
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String name;
    @NotNull
    @JdbcTypeCode(value = SqlTypes.SMALLINT)
    private BeerStyle style;
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String upc;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    private Integer quantityOnHand;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLine;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "beer_category",
               joinColumns = @JoinColumn(name = "beer_id"),
               inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBeers().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getBeers().remove(this);
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", style=" + style +
                ", upc='" + upc + '\'' +
                ", price=" + price +
                ", quantityOnHand=" + quantityOnHand +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", beerOrderLine=" + (beerOrderLine.isEmpty() ? beerOrderLine : "no order lines")+
                '}';
    }
}
