package com.gudmumic.spring.rest.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Beer {
    private UUID id;
    private Integer version;
    private String name;
    private BeerStyle style;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    Beer(UUID id, Integer version, String name, BeerStyle style, String upc, Integer quantityOnHand, BigDecimal price, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.style = style;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static BeerBuilder builder() {
        return new BeerBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public String getName() {
        return this.name;
    }

    public BeerStyle getStyle() {
        return this.style;
    }

    public String getUpc() {
        return this.upc;
    }

    public Integer getQuantityOnHand() {
        return this.quantityOnHand;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStyle(BeerStyle style) {
        this.style = style;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Beer)) return false;
        final Beer other = (Beer) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$version = this.getVersion();
        final Object other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$style = this.getStyle();
        final Object other$style = other.getStyle();
        if (this$style == null ? other$style != null : !this$style.equals(other$style)) return false;
        final Object this$upc = this.getUpc();
        final Object other$upc = other.getUpc();
        if (this$upc == null ? other$upc != null : !this$upc.equals(other$upc)) return false;
        final Object this$quantityOnHand = this.getQuantityOnHand();
        final Object other$quantityOnHand = other.getQuantityOnHand();
        if (this$quantityOnHand == null ? other$quantityOnHand != null : !this$quantityOnHand.equals(other$quantityOnHand))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$createdDate = this.getCreatedDate();
        final Object other$createdDate = other.getCreatedDate();
        if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate))
            return false;
        final Object this$updatedDate = this.getUpdatedDate();
        final Object other$updatedDate = other.getUpdatedDate();
        if (this$updatedDate == null ? other$updatedDate != null : !this$updatedDate.equals(other$updatedDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Beer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $style = this.getStyle();
        result = result * PRIME + ($style == null ? 43 : $style.hashCode());
        final Object $upc = this.getUpc();
        result = result * PRIME + ($upc == null ? 43 : $upc.hashCode());
        final Object $quantityOnHand = this.getQuantityOnHand();
        result = result * PRIME + ($quantityOnHand == null ? 43 : $quantityOnHand.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $createdDate = this.getCreatedDate();
        result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
        final Object $updatedDate = this.getUpdatedDate();
        result = result * PRIME + ($updatedDate == null ? 43 : $updatedDate.hashCode());
        return result;
    }

    public String toString() {
        return "Beer(id=" + this.getId() + ", version=" + this.getVersion() + ", name=" + this.getName() + ", style=" + this.getStyle() + ", upc=" + this.getUpc() + ", quantityOnHand=" + this.getQuantityOnHand() + ", price=" + this.getPrice() + ", createdDate=" + this.getCreatedDate() + ", updatedDate=" + this.getUpdatedDate() + ")";
    }

    public static class BeerBuilder {
        private UUID id;
        private Integer version;
        private String name;
        private BeerStyle style;
        private String upc;
        private Integer quantityOnHand;
        private BigDecimal price;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;

        BeerBuilder() {
        }

        public BeerBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public BeerBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public BeerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BeerBuilder style(BeerStyle style) {
            this.style = style;
            return this;
        }

        public BeerBuilder upc(String upc) {
            this.upc = upc;
            return this;
        }

        public BeerBuilder quantityOnHand(Integer quantityOnHand) {
            this.quantityOnHand = quantityOnHand;
            return this;
        }

        public BeerBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public BeerBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public BeerBuilder updatedDate(LocalDateTime updatedDate) {
            this.updatedDate = updatedDate;
            return this;
        }

        public Beer build() {
            return new Beer(this.id, this.version, this.name, this.style, this.upc, this.quantityOnHand, this.price, this.createdDate, this.updatedDate);
        }

        public String toString() {
            return "Beer.BeerBuilder(id=" + this.id + ", version=" + this.version + ", name=" + this.name + ", style=" + this.style + ", upc=" + this.upc + ", quantityOnHand=" + this.quantityOnHand + ", price=" + this.price + ", createdDate=" + this.createdDate + ", updatedDate=" + this.updatedDate + ")";
        }
    }
}
