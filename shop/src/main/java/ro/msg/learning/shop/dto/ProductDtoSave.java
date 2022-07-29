package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoSave {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private Integer productCategoryId;
    private Integer supplierId;
    private String imageUrl;
}
