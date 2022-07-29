package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "STOCK",
        uniqueConstraints = {@UniqueConstraint(name = "unique_stock", columnNames = {"product_id", "location_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends BaseEntity<Integer> {
    @ManyToOne
    @JsonUnwrapped
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JsonUnwrapped
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
