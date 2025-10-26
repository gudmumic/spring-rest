package com.gudmumic.spring.rest.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

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
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
    @Version
    private Integer version;
    private String name;
    @Column(length = 255)
    private String email;
    @Column(length = 255)
    private String address;
    @Column(length = 255)
    private String city;
    @Column(length = 20)
    private String zipCode;
    @Column(length = 100)
    private String country;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private Set<BeerOrder> beerOrders = new HashSet<>();

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", beerOrders=" + (beerOrders.isEmpty() ? beerOrders : "no orders") +
                '}';
    }
}
