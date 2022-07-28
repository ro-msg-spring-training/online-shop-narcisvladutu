package ro.msg.learning.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_CATEGORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory extends BaseEntity<Integer>{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
