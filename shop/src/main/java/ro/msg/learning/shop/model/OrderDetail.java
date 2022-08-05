package ro.msg.learning.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_DETAIL",
        uniqueConstraints = {@UniqueConstraint(name = "unique_stock", columnNames = {"order_id", "product_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity<Integer> {
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shipped_from", nullable = false)
    private Location location;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
